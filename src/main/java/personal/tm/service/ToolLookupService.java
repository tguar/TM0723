package personal.tm.service;

import personal.tm.model.tools.Chainsaw;
import personal.tm.model.tools.Jackhammer;
import personal.tm.model.tools.Ladder;
import personal.tm.model.tools.Tool;

import java.util.*;

/**
 * Responsible for looking up non-pricing {@link personal.tm.model.tools.Tool} information
 */
public class ToolLookupService implements ToolLookupInterface {

    /**
     * Returns first {@link personal.tm.model.tools.Tool} found from tool code. Pricing information is not included.
     * @param toolCode
     * @return {@link personal.tm.model.tools.Tool} the first found
     * @throws IllegalArgumentException if no tool found
     */
    public Tool getNonPricingToolInformationByToolCode(String toolCode){
        return tools.stream()
                .filter(x -> x.toolCode.equalsIgnoreCase(toolCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No tool with that tool code found"));
    }

    /*
    This is a set of hardcoded tools. This could also be an API call, direct database call, or something else.
    A set was used here to prevent accidental duplicate entries.
     */
    private static Set<Tool> tools =  new HashSet<>(List.of(
        new Chainsaw("CHNS", "Chainsaw", "Stihl"),
        new Ladder("LADW", "Ladder", "Werner"),
        new Jackhammer("JAKD", "Jackhammer", "DeWalt"),
        new Jackhammer("JAKR", "Jackhammer", "Ridgid")));
}
