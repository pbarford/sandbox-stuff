import com.datastax.driver.core.Cluster
import spock.lang.Specification

class CassandraTest extends Specification {
    def "test session" () {
        given:
            def cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            def session = cluster.connect();
        expect:
            session.execute("DROP KEYSPACE dev;")
            session.execute("CREATE KEYSPACE dev WITH replication " +
                            "= {'class':'SimpleStrategy', 'replication_factor':3};")
            session.execute("CREATE TABLE dev.outboundmessages (eventid int, dest text, seqno int, msg_ts bigint, headers text, data text, PRIMARY KEY((eventid, dest), seqno));")

            session.execute("insert into dev.outboundmessages(eventid, dest, seqno, msg_ts, data) values(1234567, 'nike', 1, 38574287548, '{test:test}');")


    }
}
