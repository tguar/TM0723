package personal.tm.service;

import personal.tm.model.tools.Tool;

/**
 * Contract for fetching a {@link personal.tm.model.tools.Tool} from a tool code
 */
public interface ToolLookupInterface {

    /**
     * Fetches a {@link personal.tm.model.tools.Tool} without its pricing elements
     * @param toolCode
     * @return {@link personal.tm.model.tools.Tool}
     * @throws IllegalArgumentException
     */
    Tool getNonPricingToolInformationByToolCode(String toolCode) throws IllegalArgumentException;
}
