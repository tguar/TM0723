package integration;

import org.junit.jupiter.api.*;
import personal.tm.Main;
import personal.tm.model.tools.ToolFactory;
import personal.tm.service.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class CheckoutEndToEndTests {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private CheckoutInterface checkoutService;

    @BeforeEach
    public void setUp() {
        ToolLookupInterface itemLookupService = new ToolLookupService();
        ToolFactory toolFactory = new ToolFactory(itemLookupService);
        PricingInterface pricingService = new PricingService();
        checkoutService = new CheckoutService(toolFactory, pricingService);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void when_test1_correctValuesShouldBeDisplayed(){
        Main.main(new String[]{"JAKR","5","101","9/3/15"});

        Assertions.assertEquals(
                "Discount percent must a whole number be between 0 and 100.", outputStreamCaptor.toString()
                        .trim());
    }

    @Test
    public void when_test2_correctValuesShouldBeDisplayed(){
        Main.main(new String[]{"LADW","3","10","7/2/20"});

        Assertions.assertEquals(
    """
            Tool code: LADW
            Tool type: Ladder
            Tool brand: Werner
            Rental days: 3
            Check out date: 7/2/20
            Due date: 7/4/20
            Daily rental charge: $1.99
            Charge days: 2
            Pre-discount charge: $3.98
            Discount percent: 10%
            Discount amount: $0.40
            Final charge: $3.58 """, outputStreamCaptor.toString().trim());
    }

    @Test
    public void when_test3_correctValuesShouldBeDisplayed(){
        Main.main(new String[]{"CHNS","5","25","7/2/15"});

        Assertions.assertEquals(
    """
            Tool code: CHNS
            Tool type: Chainsaw
            Tool brand: Stihl
            Rental days: 5
            Check out date: 7/2/15
            Due date: 7/6/15
            Daily rental charge: $1.49
            Charge days: 3
            Pre-discount charge: $4.47
            Discount percent: 25%
            Discount amount: $1.12
            Final charge: $3.35 """, outputStreamCaptor.toString().trim());
    }

    @Test
    public void when_test4_correctValuesShouldBeDisplayed(){
        Main.main(new String[]{"JAKD","6","0","9/3/15"});

        Assertions.assertEquals(
    """
            Tool code: JAKD
            Tool type: Jackhammer
            Tool brand: DeWalt
            Rental days: 6
            Check out date: 9/3/15
            Due date: 9/8/15
            Daily rental charge: $2.99
            Charge days: 3
            Pre-discount charge: $8.97
            Discount percent: 0%
            Discount amount: $0.00
            Final charge: $8.97 """, outputStreamCaptor.toString().trim());
    }



    @Test
    public void when_test5_correctValuesShouldBeDisplayed(){
        Main.main(new String[]{"JAKR","9","0","7/2/15"});

        Assertions.assertEquals(
    """
            Tool code: JAKR
            Tool type: Jackhammer
            Tool brand: Ridgid
            Rental days: 9
            Check out date: 7/2/15
            Due date: 7/10/15
            Daily rental charge: $2.99
            Charge days: 6
            Pre-discount charge: $17.94
            Discount percent: 0%
            Discount amount: $0.00
            Final charge: $17.94 """, outputStreamCaptor.toString().trim());
    }

    @Test
    public void when_test6_correctValuesShouldBeDisplayed(){
        Main.main(new String[]{"JAKR","4","50","7/2/20"});

        Assertions.assertEquals(
    """
            Tool code: JAKR
            Tool type: Jackhammer
            Tool brand: Ridgid
            Rental days: 4
            Check out date: 7/2/20
            Due date: 7/5/20
            Daily rental charge: $2.99
            Charge days: 1
            Pre-discount charge: $2.99
            Discount percent: 50%
            Discount amount: $1.50
            Final charge: $1.49 """, outputStreamCaptor.toString().trim());
    }
}
