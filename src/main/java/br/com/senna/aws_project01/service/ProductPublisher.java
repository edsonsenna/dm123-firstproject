package br.com.senna.aws_project01.service;

import br.com.senna.aws_project01.enums.EventType;
import br.com.senna.aws_project01.model.Envelop;
import br.com.senna.aws_project01.model.Product;
import br.com.senna.aws_project01.model.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ProductPublisher {

    private static final Logger log = LoggerFactory.getLogger(
            ProductPublisher.class);
    private AmazonSNS snsClient;
    private Topic productEventsTopic;
    private ObjectMapper objectMapper;

    @Autowired
    public ProductPublisher(AmazonSNS snsClient,
                            @Qualifier("productEventsTopic")Topic productEventsTopic,
                            ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.productEventsTopic = productEventsTopic;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType, String username) {
        ProductEvent productEvent = new ProductEvent();
        productEvent.setProductId(product.getId());
        productEvent.setCode(product.getCode());
        productEvent.setUsername(username);
        Envelop envelop = new Envelop();
        envelop.setEventType(eventType);
        try {
            envelop.setData(objectMapper.writeValueAsString(productEvent));
            PublishResult publishResult = snsClient.publish(
                    productEventsTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelop));
            log.info("Product event sent - ProductId: {} - MessageId: {}",
                    product.getId(), publishResult.getMessageId());

        } catch (JsonProcessingException e) {
            log.error("Failed to create product event message");
        }
    }
}
