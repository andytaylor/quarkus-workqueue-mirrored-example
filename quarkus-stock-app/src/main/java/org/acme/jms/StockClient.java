package org.acme.jms;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import org.acme.model.StockItem;

/**
 * A bean consuming stock from the JMS queue.
 */
@ApplicationScoped
public class StockClient {

    @Inject
    ConnectionFactory connectionFactory;


    /*void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }*/

    public List<StockItem> getStock() throws Exception {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSProducer producer = context.createProducer();
            TemporaryQueue replyQueue = context.createTemporaryQueue();
            Message stockQuery = context.createMessage();
            stockQuery.setJMSReplyTo(replyQueue);
            producer.send(context.createQueue("stockqueryqueue"), stockQuery);
            JMSConsumer consumer = context.createConsumer(replyQueue);
            Message message = consumer.receive();
            if (message == null) {
                // receive returns `null` if the JMSConsumer is closed
                throw new Exception("stock query failed");
            }
            return null;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
