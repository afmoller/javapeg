package moller.javapeg.test.unittest.config;

import moller.javapeg.program.config.ConfigUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TestConfigUtil {

    @Test
    public void testIsClientIdSet() {
        Assert.assertFalse(ConfigUtil.isClientIdSet("NOT-DEFINED"));
        Assert.assertTrue(ConfigUtil.isClientIdSet(UUID.randomUUID().toString()));
    }

    @Test
    public void testGenerateClientId() {
        String clientId = ConfigUtil.generateClientId();

        Assert.assertTrue(clientId.matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
    }
}
