/*
 * #%L
 * BroadleafCommerce Framework
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
package org.broadleafcommerce.core.search.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andre Azzolini (apazzolini)
 */
public class SearchFacetTreeResultDTO extends SearchFacetResultDTO {

	protected SearchFacetResultDTO facetResultDTO = new SearchFacetResultDTO();

	protected List<SearchFacetTreeResultDTO> children = new ArrayList<SearchFacetTreeResultDTO>();

	protected String displayValue;

	public SearchFacetTreeResultDTO() {
		super();
		this.facetResultDTO = new SearchFacetResultDTO();
		this.children = new ArrayList<SearchFacetTreeResultDTO>();
	}

	public SearchFacetResultDTO getFacetResultDTO() {
		return facetResultDTO;
	}

	public void setFacetResultDTO(SearchFacetResultDTO facetResultDTO) {
		this.facetResultDTO = facetResultDTO;
	}

	public List<SearchFacetTreeResultDTO> getChildren() {
		return children;
	}

	public void addChildren(SearchFacetTreeResultDTO searchFacetTreeResultDTO) {
		this.children.add(searchFacetTreeResultDTO);
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public void copyBeanFromSearchFacetResultDTO(SearchFacetResultDTO fromBean) {
		this.facet = facet;
		this.value = value;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.quantity = quantity;
		this.active = active;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!getClass().isAssignableFrom(obj.getClass()))
			return false;
		SearchFacetTreeResultDTO other = (SearchFacetTreeResultDTO) obj;

		if (value != null && other.value != null) {
			return value.equals(other.value);
		}

		return true;
	}
}
