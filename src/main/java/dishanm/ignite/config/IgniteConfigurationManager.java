package dishanm.ignite.config;

import dishanm.ignite.Constants;
import dishanm.ignite.beans.Item;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IgniteConfigurationManager {

    public static IgniteConfiguration getConfiguration() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("ItemsGrid");
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setLocalHost("127.0.0.1");

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        tcpDiscoverySpi.setIpFinder(ipFinder);
        tcpDiscoverySpi.setLocalPortRange(9);
        igniteConfiguration.setDiscoverySpi(tcpDiscoverySpi);

        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalAddress("localhost");
        communicationSpi.setLocalPort(48100);
        communicationSpi.setSlowClientQueueLimit(1000);
        igniteConfiguration.setCommunicationSpi(communicationSpi);
        igniteConfiguration.setCacheConfiguration(cacheConfiguration());

        return igniteConfiguration;
    }

    private static CacheConfiguration[] cacheConfiguration() {
        List<CacheConfiguration> cacheConfigurations = new ArrayList<>();
        CacheConfiguration<Long, Item> cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED);
        cacheConfiguration.setName(Constants.ITEM_CACHE_NAME);
        cacheConfiguration.setWriteThrough(false);
        cacheConfiguration.setReadThrough(false);
        cacheConfiguration.setWriteBehindEnabled(false);
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setStatisticsEnabled(true);
        cacheConfiguration.setIndexedTypes(Long.class, Item.class);
        cacheConfigurations.add(cacheConfiguration);

        return cacheConfigurations.toArray(new CacheConfiguration[cacheConfigurations.size()]);
    }
}
