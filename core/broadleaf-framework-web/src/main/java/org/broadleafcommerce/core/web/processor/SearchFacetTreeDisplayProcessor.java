/*
 * #%L
 * BroadleafCommerce Framework Web
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.broadleafcommerce.core.web.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetResultDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetTreeResultDTO;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractLocalVariableDefinitionElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * @author Priyesh Patel
 */
public class SearchFacetTreeDisplayProcessor extends AbstractLocalVariableDefinitionElementProcessor {

	/**
	 * Sets the name of this processor to be used in Thymeleaf template
	 */
	public SearchFacetTreeDisplayProcessor() {
		super("search_facet_tree_display");
	}

	@Override
	public int getPrecedence() {
		return 100;
	}

	protected void initServices(Arguments arguments) {

	}

	@Override
	protected Map<String, Object> getNewLocalVariables(Arguments arguments, Element element) {
		initServices(arguments);
		ArrayList<SearchFacetTreeResultDTO> facetTreeDisplayValues = new ArrayList<SearchFacetTreeResultDTO>();
		Map<String, Object> newVars = new HashMap<String, Object>();
		Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration()).parseExpression(arguments.getConfiguration(), arguments,
				element.getAttributeValue("searchFacetDto"));
		Object facetDto = expression.execute(arguments.getConfiguration(), arguments);
		if (facetDto instanceof SearchFacetDTO) {
			SearchFacetDTO searchFacetDto = (SearchFacetDTO) facetDto;
			for (SearchFacetResultDTO facetValue : searchFacetDto.getFacetValues()) {
				String[] areas = facetValue.getValue().split(", ");
				for (int i = 0; i <= areas.length - 1; i++) {
					boolean hasChildren = false;
					SearchFacetTreeResultDTO tempTreeNode = new SearchFacetTreeResultDTO();
					tempTreeNode.setDisplayValue(areas[i]);
					if (i < areas.length - 1) {
						tempTreeNode.setValue(areas[i]);
						hasChildren = true;
					} else {
						tempTreeNode.setValue(facetValue.getValue());
						hasChildren = false;
					}
					if (facetTreeDisplayValues.contains(tempTreeNode)) {
						if (hasChildren) {
							SearchFacetTreeResultDTO updateTreeNode = facetTreeDisplayValues.get(facetTreeDisplayValues.indexOf(tempTreeNode));
							updateTreeNode.setQuantity(updateTreeNode.getQuantity() + facetValue.getQuantity());
						}
					} else {
						SearchFacetTreeResultDTO parentTreeNode = new SearchFacetTreeResultDTO();
						tempTreeNode.setFacetResultDTO(facetValue);
						if (i != 0) {
							parentTreeNode.setValue(areas[i - 1]);
						}
						if (hasChildren) {
							if (i == 0) {
								tempTreeNode.setQuantity(facetValue.getQuantity());
								facetTreeDisplayValues.add(tempTreeNode);
							} else {
								tempTreeNode.setQuantity(facetValue.getQuantity());
								facetTreeDisplayValues.get(facetTreeDisplayValues.indexOf(parentTreeNode)).addChildren(tempTreeNode);
							}
						} else {
							facetTreeDisplayValues.get(facetTreeDisplayValues.indexOf(parentTreeNode)).addChildren(tempTreeNode);
						}
					}
				}
			}
		}
		newVars.put("facetTreeDisplayValues", facetTreeDisplayValues);

		return newVars;
	}

	@Override
	protected boolean removeHostElement(Arguments arguments, Element element) {
		return false;
	}
}
