package moller.javapeg.program.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ConfigUtilTest {

    @Test
    public void testIsClientIdSet() {
        Assertions.assertFalse(ConfigUtil.isClientIdSet("NOT-DEFINED"));
        Assertions.assertTrue(ConfigUtil.isClientIdSet(UUID.randomUUID().toString()));
    }

    @Test
    public void testGenerateClientId() {
        String clientId = ConfigUtil.generateClientId();

        Assertions.assertTrue(clientId.matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
    }

}