package ru.stepanenko.tm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.Domain;
import ru.stepanenko.tm.util.HashUtil;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.StringValidator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class UserService extends AbstractEntityService<User, IUserRepository> implements IUserService {

    @Nullable
    private User currentUser;

    @NotNull
    private IProjectRepository projectRepository;

    @NotNull
    private ITaskRepository taskRepository;

    public UserService(@NotNull final IUserRepository userRepository,
                       @NotNull final IProjectRepository projectRepository,
                       @NotNull final ITaskRepository taskRepository) {
        super(userRepository);
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public User create(@NotNull final String login, @NotNull final String password, @NotNull final String role) {
        if (!StringValidator.validate(login, password, role)) return null;
        if (EnumUtil.stringToRole(role) == null) return null;
        return repository.persist(new User(login, HashUtil.md5(password), EnumUtil.stringToRole(role)));
    }

    @Override
    public User edit(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role) {
        if (!StringValidator.validate(id, login, password, role)) return null;
        if (EnumUtil.stringToRole(role) == null) return null;
        @NotNull
        User user = findOne(id);
        user.setLogin(login);
        user.setPassword(HashUtil.md5(password));
        user.setRole(EnumUtil.stringToRole(role));
        return repository.merge(user);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public User findByLogin(@NotNull final String login) {
        if (!StringValidator.validate(login)) return null;
        return repository.findByLogin(login);
    }

    @Override
    public void setCurrentUser(@Nullable final User user) {
        this.currentUser = user;
    }

    @Override
    public User authenticationUser(@NotNull final String login, @NotNull final String password) {
        @Nullable
        User user = findByLogin(login);
        if (user == null || !HashUtil.md5(password).equals(user.getPassword())) return null;
        setCurrentUser(user);
        return user;
    }

    @Override
    public void load() {
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream("tm-server/data.out"))){
            Domain domain = (Domain) oin.readObject();
            projectRepository.recovery(domain.getProjects());
            taskRepository.recovery(domain.getTasks());
            repository.recovery(domain.getUsers());
            System.out.println("Success all data load!");
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        Domain domain = new Domain(new ArrayList<>(projectRepository.findAll()), new ArrayList<>(taskRepository.findAll()), new ArrayList<>(repository.findAll()));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tm-server/data.out"))){
            oos.writeObject(domain);
            oos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void loadJaxbXml() {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(Domain.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Domain domain = (Domain)unmarshaller.unmarshal( new File("tm-server/data.xml") );
            projectRepository.recovery(domain.getProjects());
            taskRepository.recovery(domain.getTasks());
            repository.recovery(domain.getUsers());
            System.out.println("Success all data load!");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveJaxbXml() {
        Domain domain = new Domain(new ArrayList<>(projectRepository.findAll()), new ArrayList<>(taskRepository.findAll()), new ArrayList<>(repository.findAll()));
        try(FileWriter fw = new FileWriter("tm-server/data.xml")){
            fw.write(domainToXMLString(domain));
            System.out.println("Success, fll data save in file!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String domainToXMLString(Domain domain){
        try{
            JAXBContext context = JAXBContext.newInstance(Domain.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            StringWriter sw = new StringWriter();

            marshaller.marshal(domain, sw);
            return xmlToPretty(sw.toString(), 2);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String xmlToPretty(String xml, int indent) {
        try {
            // Turn xml string into a document
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

            // Remove whitespaces outside tags
            document.normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
                    document,
                    XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            // Setup pretty print options
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Return pretty print xml string
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void loadJaxbJSON() {
        try {
            Map<String, Object> properties = new HashMap<String, Object>(3);
            properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            properties.put(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
            properties.put(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, Boolean.TRUE);
            JAXBContext context = JAXBContextFactory.createContext(new Class[]{Domain.class, Project.class, Task.class, User.class}, properties);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StreamSource json = new StreamSource("tm-server/data.json");
            Domain domain = unmarshaller.unmarshal(json, Domain.class).getValue();
            projectRepository.recovery(domain.getProjects());
            taskRepository.recovery(domain.getTasks());
            repository.recovery(domain.getUsers());
            System.out.println("Success all data load!");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveJaxbJSON() {
        Domain domain = new Domain(new ArrayList<>(projectRepository.findAll()), new ArrayList<>(taskRepository.findAll()), new ArrayList<>(repository.findAll()));

        try(FileWriter fw = new FileWriter("tm-server/data.json")){
            fw.write(domainToJsonString(domain));
            System.out.println("Success, all data save in file!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String domainToJsonString(Domain domain) {
        try {
            Map<String, Object> properties = new HashMap<String, Object>(3);
            properties.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            properties.put(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
            properties.put(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, Boolean.TRUE);
            JAXBContext context = JAXBContextFactory.createContext(new Class[]{Domain.class}, properties);
            Marshaller marshaller = context.createMarshaller();
            StringWriter sw = new StringWriter();
            marshaller.marshal(domain, sw);
            return jsonToPretty(sw.toString(), 2);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String jsonToPretty(String jsonString, int indent) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");
            scriptEngine.put("jsonString", jsonString);
            scriptEngine.eval("result = JSON.stringify(JSON.parse(jsonString), null, "+indent+")");
            return  (String) scriptEngine.get("result");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadFasterXml() {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            Domain domain = xmlMapper.readValue(new File("tm-server/data.xml"), Domain.class);
            projectRepository.recovery(domain.getProjects());
            taskRepository.recovery(domain.getTasks());
            repository.recovery(domain.getUsers());
            System.out.println("Success all data load!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFasterXml() {
        Domain domain = new Domain(new ArrayList<>(projectRepository.findAll()), new ArrayList<>(taskRepository.findAll()), new ArrayList<>(repository.findAll()));
        XmlMapper mapper = new XmlMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("tm-server/data.xml"), domain);
            System.out.println("Success all data was save!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFasterJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Domain domain = mapper.readValue(new File("tm-server/data.json"), Domain.class);
            projectRepository.recovery(domain.getProjects());
            taskRepository.recovery(domain.getTasks());
            repository.recovery(domain.getUsers());
            System.out.println("Success all data load!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFasterJSON() {
        Domain domain = new Domain(new ArrayList<>(projectRepository.findAll()), new ArrayList<>(taskRepository.findAll()), new ArrayList<>(repository.findAll()));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("tm-server/data.json"), domain);
            System.out.println("Success all data was save!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
