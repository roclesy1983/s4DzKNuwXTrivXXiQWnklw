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
package org.broadleafcommerce.core.catalog.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.copy.CreateResponse;
import org.broadleafcommerce.common.copy.MultiTenantCopyContext;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductImpl;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Jeff Fischer
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_CUSTOMER_XREF")
@AdminPresentationClass(excludeFromPolymorphism = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blProducts")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class ProductCustomerXrefImpl implements ProductCustomerXref {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator= "ProductCustomerXrefId")
    @GenericGenerator(
        name="ProductCustomerXrefId",
        strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="ProductCustomerXrefImpl"),
            @Parameter(name="entity_name", value="org.broadleafcommerce.core.catalog.domain.ProductCustomerXrefImpl")
        }
    )
    @Column(name = "PRODUCT_CUSTOMER_XREF_ID")
    protected Long id;

    @OneToOne(targetEntity = ProductImpl.class, optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product = new ProductImpl();

    @OneToOne(targetEntity = CustomerImpl.class, optional=false)
    @JoinColumn(name = "CUSTOMER_ID")
    protected Customer customer = new CustomerImpl();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer productOption) {
        this.customer = productOption;
    }

    @Override
    public <G extends ProductCustomerXref> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        ProductCustomerXref cloned = createResponse.getClone();
        if (product != null) {
            cloned.setProduct(product.createOrRetrieveCopyInstance(context).getClone());
        }
        if (customer != null) {
            cloned.setCustomer(customer.createOrRetrieveCopyInstance(context).getClone());
        }

        return createResponse;
    }
}
