/**
 * Follow up:
 * Now imagine some memberships introduce conflicting benefits.
 * For example, a Prime customer gets 10% off shipping, while a Business customer gets 20% off shipping.
 * The customer should receive only the highest applicable discount, not both.
 */


enum BenefitType {
    SHIPPING_DISCOUNT,
    STORAGE_DISCOUNT
}

record Benefit (
        BenefitType type,
        int percentage
) {}

interface BenefitProvider {
    List<Benefit> getBenefits(Customer customer)
}

class PrimeBenefitProvider implements BenefitProvider {
    @Override
    public List<Benefit> getBenefits(Customer customer) {
        return List.of(
                new Benefit(BenefitType.SHIPPING_DISCOUNT, 10)
        );
    }
}

class BusinessBenefitProvider implements BenefitProvider {
    @Override
    public List<Benefit> getBenefits(Customer customer) {
        return List.of(
                new Benefit(BenefitType.SHIPPING_DISCOUNT, 20)
        );
    }
}

class BenefitResolver {
    public List<Benefit> resolve(List<Benefit> benefits) {
        Map<BenefitType, Benefit> best = new HashMap<>();
        for(Benefit benefit: benefits) {
            Benefit existing = best.get(benefit.type());
            if (existing == null || benefit.percentage() > existing.percentage()) {
                best.put(benefit.type(), benefit);
            }
        }
        return new ArrayList<>(best.values());
    }
}



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