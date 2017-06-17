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
package org.broadleafcommerce.core.catalog.dao;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductCustomerXref;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.springframework.stereotype.Repository;
import org.broadleafcommerce.core.order.domain.Order;

@Repository("blProductCustomerXrefDao")
public class ProductCustomerXrefDaoImpl implements ProductCustomerXrefDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Resource(name = "blEntityConfiguration")
	protected EntityConfiguration entityConfiguration;

	public ProductCustomerXref saveProductCustomerXref(ProductCustomerXref productCustomerXref) {
		return em.merge(productCustomerXref);
	}

	@Override
	public Product readProductByCustomerId(Long customerId) {
		Query query = em.createNamedQuery("BC_READ_PRODUCT_CUSTOMER_XREF_BY_CUSTOMERID");
		query.setParameter("customerId", customerId);
		return ((ProductCustomerXref) query.getResultList().get(0)).getProduct();
	}

	@Override
	public Customer readCustomerByOrder(Order order) {
		Query query = em.createNamedQuery("BC_READ_CUSTOMERS_XREF_BY_ORDER_NUMBER");
		query.setParameter("order", order);
		return ((ProductCustomerXref) query.getResultList().get(0)).getCustomer();
	}

}
