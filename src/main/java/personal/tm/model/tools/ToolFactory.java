package personal.tm.model.tools;

import personal.tm.service.ToolLookupInterface;

/**
 * ToolFactory is responsible for generating Tools.
 */
public class ToolFactory {

    private final ToolLookupInterface itemLookup;

    /**
     * Constructor
     * @param itemLookup
     */
    public ToolFactory(ToolLookupInterface itemLookup){
        this.itemLookup = itemLookup;
    }

    /**
     * Returns instantiated tool subclass based on tool code lookup.
     * @param toolCode the tool code
     * @return Tool subclass
     * @throws IllegalArgumentException if Tool is not found
     */
    public Tool generateToolByToolCode(String toolCode) throws IllegalArgumentException {
        if(null == toolCode){
            throw new IllegalArgumentException("You must provide a Tool Code. Examples include 'CHNS', 'LADW'.");
        }
        Tool tool = itemLookup.getNonPricingToolInformationByToolCode(toolCode);

        if (tool.toolType.equalsIgnoreCase("LADDER")) {
            return new Ladder(tool.toolCode, tool.toolType, tool.brand);

        }
        if (tool.toolType.equalsIgnoreCase("CHAINSAW")) {
            return new Chainsaw(tool.toolCode, tool.toolType, tool.brand);
        }
        if (tool.toolType.equalsIgnoreCase("JACKHAMMER")) {
            return new Jackhammer(tool.toolCode, tool.toolType, tool.brand);
        }
        else {
            throw new IllegalArgumentException("You must provide a valid Tool Code. Examples include 'CHNS', 'LADW'.");
        }
    }
}
