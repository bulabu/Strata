/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.finance.swap;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.DerivedProperty;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.platform.finance.Trade;
import com.opengamma.platform.finance.TradeType;
import com.opengamma.platform.source.id.StandardId;

/**
 * A trade representing an interest rate swap.
 * <p>
 * A trade in a {@link Swap}.
 * For example, a trade involving a fixed vs 3 month Libor interest rate swap.
 */
@BeanDefinition
public final class SwapTrade
    implements Trade, ImmutableBean, Serializable {

  /**
   * The trade type constant for this class - 'Swap'.
   */
  public static final TradeType TYPE = TradeType.of("Swap");

  /**
   * The primary standard identifier for the trade.
   * <p>
   * The standard identifier is used to identify the trade.
   * It will typically be an identifier in an external data system.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final StandardId standardId;
  /**
   * The set of additional trade attributes.
   * <p>
   * Most data in the trade is available as bean properties.
   * Attributes are typically used to tag the object with additional information.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final ImmutableMap<String, String> attributes;
  /**
   * The trade date.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final LocalDate tradeDate;

  /**
   * The swap that was agreed when the trade occurred.
   * <p>
   * The swap typically has a start date shortly after the trade date,
   * however this is not required. Swaps that start before the trade date
   * or in the future are also supported by the data model.
   */
  @PropertyDefinition(validate = "notNull")
  private final Swap swap;

  //-------------------------------------------------------------------------
  /**
   * Gets the trade type.
   * 
   * @return {@link #TYPE}
   */
  @Override
  @DerivedProperty
  public TradeType getTradeType() {
    return TYPE;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SwapTrade}.
   * @return the meta-bean, not null
   */
  public static SwapTrade.Meta meta() {
    return SwapTrade.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SwapTrade.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static SwapTrade.Builder builder() {
    return new SwapTrade.Builder();
  }

  private SwapTrade(
      StandardId standardId,
      Map<String, String> attributes,
      LocalDate tradeDate,
      Swap swap) {
    JodaBeanUtils.notNull(standardId, "standardId");
    JodaBeanUtils.notNull(attributes, "attributes");
    JodaBeanUtils.notNull(tradeDate, "tradeDate");
    JodaBeanUtils.notNull(swap, "swap");
    this.standardId = standardId;
    this.attributes = ImmutableMap.copyOf(attributes);
    this.tradeDate = tradeDate;
    this.swap = swap;
  }

  @Override
  public SwapTrade.Meta metaBean() {
    return SwapTrade.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the primary standard identifier for the trade.
   * <p>
   * The standard identifier is used to identify the trade.
   * It will typically be an identifier in an external data system.
   * @return the value of the property, not null
   */
  @Override
  public StandardId getStandardId() {
    return standardId;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the set of additional trade attributes.
   * <p>
   * Most data in the trade is available as bean properties.
   * Attributes are typically used to tag the object with additional information.
   * @return the value of the property, not null
   */
  @Override
  public ImmutableMap<String, String> getAttributes() {
    return attributes;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the trade date.
   * @return the value of the property, not null
   */
  @Override
  public LocalDate getTradeDate() {
    return tradeDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the swap that was agreed when the trade occurred.
   * <p>
   * The swap typically has a start date shortly after the trade date,
   * however this is not required. Swaps that start before the trade date
   * or in the future are also supported by the data model.
   * @return the value of the property, not null
   */
  public Swap getSwap() {
    return swap;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SwapTrade other = (SwapTrade) obj;
      return JodaBeanUtils.equal(getStandardId(), other.getStandardId()) &&
          JodaBeanUtils.equal(getAttributes(), other.getAttributes()) &&
          JodaBeanUtils.equal(getTradeDate(), other.getTradeDate()) &&
          JodaBeanUtils.equal(getSwap(), other.getSwap());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getStandardId());
    hash = hash * 31 + JodaBeanUtils.hashCode(getAttributes());
    hash = hash * 31 + JodaBeanUtils.hashCode(getTradeDate());
    hash = hash * 31 + JodaBeanUtils.hashCode(getSwap());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(192);
    buf.append("SwapTrade{");
    buf.append("standardId").append('=').append(getStandardId()).append(',').append(' ');
    buf.append("attributes").append('=').append(getAttributes()).append(',').append(' ');
    buf.append("tradeDate").append('=').append(getTradeDate()).append(',').append(' ');
    buf.append("swap").append('=').append(getSwap()).append(',').append(' ');
    buf.append("tradeType").append('=').append(JodaBeanUtils.toString(getTradeType()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SwapTrade}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code standardId} property.
     */
    private final MetaProperty<StandardId> standardId = DirectMetaProperty.ofImmutable(
        this, "standardId", SwapTrade.class, StandardId.class);
    /**
     * The meta-property for the {@code attributes} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<String, String>> attributes = DirectMetaProperty.ofImmutable(
        this, "attributes", SwapTrade.class, (Class) ImmutableMap.class);
    /**
     * The meta-property for the {@code tradeDate} property.
     */
    private final MetaProperty<LocalDate> tradeDate = DirectMetaProperty.ofImmutable(
        this, "tradeDate", SwapTrade.class, LocalDate.class);
    /**
     * The meta-property for the {@code swap} property.
     */
    private final MetaProperty<Swap> swap = DirectMetaProperty.ofImmutable(
        this, "swap", SwapTrade.class, Swap.class);
    /**
     * The meta-property for the {@code tradeType} property.
     */
    private final MetaProperty<TradeType> tradeType = DirectMetaProperty.ofDerived(
        this, "tradeType", SwapTrade.class, TradeType.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "standardId",
        "attributes",
        "tradeDate",
        "swap",
        "tradeType");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          return standardId;
        case 405645655:  // attributes
          return attributes;
        case 752419634:  // tradeDate
          return tradeDate;
        case 3543443:  // swap
          return swap;
        case 752919230:  // tradeType
          return tradeType;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public SwapTrade.Builder builder() {
      return new SwapTrade.Builder();
    }

    @Override
    public Class<? extends SwapTrade> beanType() {
      return SwapTrade.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code standardId} property.
     * @return the meta-property, not null
     */
    public MetaProperty<StandardId> standardId() {
      return standardId;
    }

    /**
     * The meta-property for the {@code attributes} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<String, String>> attributes() {
      return attributes;
    }

    /**
     * The meta-property for the {@code tradeDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> tradeDate() {
      return tradeDate;
    }

    /**
     * The meta-property for the {@code swap} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Swap> swap() {
      return swap;
    }

    /**
     * The meta-property for the {@code tradeType} property.
     * @return the meta-property, not null
     */
    public MetaProperty<TradeType> tradeType() {
      return tradeType;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          return ((SwapTrade) bean).getStandardId();
        case 405645655:  // attributes
          return ((SwapTrade) bean).getAttributes();
        case 752419634:  // tradeDate
          return ((SwapTrade) bean).getTradeDate();
        case 3543443:  // swap
          return ((SwapTrade) bean).getSwap();
        case 752919230:  // tradeType
          return ((SwapTrade) bean).getTradeType();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code SwapTrade}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<SwapTrade> {

    private StandardId standardId;
    private Map<String, String> attributes = new HashMap<String, String>();
    private LocalDate tradeDate;
    private Swap swap;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(SwapTrade beanToCopy) {
      this.standardId = beanToCopy.getStandardId();
      this.attributes = new HashMap<String, String>(beanToCopy.getAttributes());
      this.tradeDate = beanToCopy.getTradeDate();
      this.swap = beanToCopy.getSwap();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          return standardId;
        case 405645655:  // attributes
          return attributes;
        case 752419634:  // tradeDate
          return tradeDate;
        case 3543443:  // swap
          return swap;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1284477768:  // standardId
          this.standardId = (StandardId) newValue;
          break;
        case 405645655:  // attributes
          this.attributes = (Map<String, String>) newValue;
          break;
        case 752419634:  // tradeDate
          this.tradeDate = (LocalDate) newValue;
          break;
        case 3543443:  // swap
          this.swap = (Swap) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public SwapTrade build() {
      return new SwapTrade(
          standardId,
          attributes,
          tradeDate,
          swap);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code standardId} property in the builder.
     * @param standardId  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder standardId(StandardId standardId) {
      JodaBeanUtils.notNull(standardId, "standardId");
      this.standardId = standardId;
      return this;
    }

    /**
     * Sets the {@code attributes} property in the builder.
     * @param attributes  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder attributes(Map<String, String> attributes) {
      JodaBeanUtils.notNull(attributes, "attributes");
      this.attributes = attributes;
      return this;
    }

    /**
     * Sets the {@code tradeDate} property in the builder.
     * @param tradeDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder tradeDate(LocalDate tradeDate) {
      JodaBeanUtils.notNull(tradeDate, "tradeDate");
      this.tradeDate = tradeDate;
      return this;
    }

    /**
     * Sets the {@code swap} property in the builder.
     * @param swap  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder swap(Swap swap) {
      JodaBeanUtils.notNull(swap, "swap");
      this.swap = swap;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("SwapTrade.Builder{");
      buf.append("standardId").append('=').append(JodaBeanUtils.toString(standardId)).append(',').append(' ');
      buf.append("attributes").append('=').append(JodaBeanUtils.toString(attributes)).append(',').append(' ');
      buf.append("tradeDate").append('=').append(JodaBeanUtils.toString(tradeDate)).append(',').append(' ');
      buf.append("swap").append('=').append(JodaBeanUtils.toString(swap));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
