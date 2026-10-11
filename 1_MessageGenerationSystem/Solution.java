/**
 * Interview Question
 * OOD · 35 min
 *
 * Design a message generation system for an e-commerce platform.
 * The system needs to generate personalized messages for different customer types, such as regular, Prime, and business customers.
 * It should be easy to introduce new customer types or message formats without modifying existing business logic.
 *
 * Clarify requirement:
 *
 * 1. Are message templates fixed for each customer type, or can they vary by event?
 * 2. Do we only generate message content, or are we also responsible for sending messages?
 * 3. Are customer types mutually exclusive?
 */

/**
 * Design:
 *
 * - Composition is better than inheritance, use MessageGenerator interface,
 * - `Customer`：Store user information
 * - `MessageGenerator`：Define abstract interface
 * - `RegularGenerator`、`PrimeGenerator`、`BusinessGenerator`：Generate message independently
 * - `MessageService`：Find the right strategy and use that
 */

/**
 * Follow up:
 * 1. A customer can be both a Prime and a Business customer, how would I modify code to support multiple memberships without creating separate class
 * - For each generator class, only generate the customer type specific test, and concat in MessageService
 * - In customer type, it should be a membership type list
 */


public Customer {
    String customerId;
    List<CustomerType> customerTypeList;
}

public interface MessageGenerator {
    String generate(Customer customer)
}

public class PrimeMessageGenerator implements MessageGenerator{

    @Override
    public String generate(Customer customer) {
        return "enjoy your Prime benefits!";
    }
}

public class BusinessMessageGenerator implements MessageGenerator {
    @Override
    public String generate(Customer customer) {
        return "enjoy your Business benefits!";
    }
}

public class MessageService {
    private final Map<CustomerType, MessageGenerator> generators;

    public MessageService (Map<CustomerType, MessageGenerator> generators) {
        this.generators = Map.copyOf(generators)
    }

    public String generateMessage(Customer customer) {

        List<CustomerType> customerTypeList = customer.getCustomerTypeList();

        StringBuilder message = new StringBuilder();
        message.append("Hello").append(customer.getName()).append("!");

        for(CustomerType customerType: customerTypeList){
            MessageGenerator generator = generators.get(customerType);
            if (generator == null) {
                throw new IllegalArgumentException("unsupported customer type")
            }
            message.append(" ");
            message.append(generator.generate(customer));

        }
        return message.toString()
    }
}