package personal.tm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import personal.tm.model.tools.Tool;

/**
 * Responsible for testing {@link personal.tm.service.ToolLookupService}
 */
public class ToolLookupServiceTest {

    ToolLookupService toolLookupService = new ToolLookupService();

    @Test
    public void whenToolExists_getNonPricingToolInformationByToolCode_shouldReturnTool(){
        //Arrange
        //Act
        Tool result = toolLookupService.getNonPricingToolInformationByToolCode("CHNS");

        //Assert
        Assertions.assertAll("All values should be correct",
                () -> Assertions.assertEquals("Stihl", result.brand, "Tool brand should match"),
                () -> Assertions.assertEquals("CHNS", result.toolCode, "Tool Code should match"),
                () -> Assertions.assertEquals("Chainsaw",result.toolType, "Tool Type should match"));

    }

    @Test
    public void whenToolDoesNotExist_getNonPricingToolInformationByToolCode_shouldThrow(){
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            toolLookupService.getNonPricingToolInformationByToolCode("TOOLCODE_THAT_DOES_NOT_EXIST");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("No tool with that tool code found"), "Correct exception message returned");
    }
}
