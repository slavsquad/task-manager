package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import ru.stepanenko.tm.api.UnitTest;

import static org.junit.Assert.*;

@Category(UnitTest.class)
public class HashUtilTest {

    @Test
    public void md5() {
        @NotNull final String password = "password";
        @NotNull final String hash = HashUtil.md5(password);
        assertNotNull(hash);
        assertEquals(hash, HashUtil.md5(password));
    }
}