/*
 * Copyright (C) 2017 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.etd;

import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.coverBeanEquals;
import static com.opengamma.strata.collect.TestHelper.coverImmutableBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.YearMonth;

import org.testng.annotations.Test;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.product.SecurityId;
import com.opengamma.strata.product.SecurityPriceInfo;
import com.opengamma.strata.product.common.ExchangeIds;
import com.opengamma.strata.product.common.PutCall;

/**
 * Test {@link EtdContractSpec}.
 */
@Test
public class EtdContractSpecTest {

  private static final EtdContractSpec FUTURE_CONTRACT = sut();
  private static final EtdContractSpec OPTION_CONTRACT = sut2();

  //-------------------------------------------------------------------------
  public void createFutureAutoId() {
    EtdFutureSecurity security = FUTURE_CONTRACT.createFuture(YearMonth.of(2015, 6), "");

    assertThat(security.getSecurityId()).isEqualTo(SecurityId.of(EtdIdUtils.ETD_SCHEME, "F-ECAG-FOO-201506"));
    assertThat(security.getExpiryMonth()).isEqualTo(YearMonth.of(2015, 6));
    assertThat(security.getContractSpecId()).isEqualTo(FUTURE_CONTRACT.getId());
    assertThat(security.getExpiryDateCode()).isEmpty();
    assertThat(security.getInfo().getPriceInfo()).isEqualTo(FUTURE_CONTRACT.getPriceInfo());
  }

  public void createFutureNonStandardAutoId() {
    EtdFutureSecurity security = FUTURE_CONTRACT.createFuture(YearMonth.of(2015, 6), "W1");

    assertThat(security.getSecurityId()).isEqualTo(SecurityId.of(EtdIdUtils.ETD_SCHEME, "F-ECAG-FOO-201506W1"));
    assertThat(security.getExpiryMonth()).isEqualTo(YearMonth.of(2015, 6));
    assertThat(security.getContractSpecId()).isEqualTo(FUTURE_CONTRACT.getId());
    assertThat(security.getExpiryDateCode()).isEqualTo("W1");
    assertThat(security.getInfo().getPriceInfo()).isEqualTo(FUTURE_CONTRACT.getPriceInfo());
  }

  public void createFutureManualId() {
    EtdFutureSecurity security = FUTURE_CONTRACT.createFuture(YearMonth.of(2015, 6), "", SecurityId.of("A", "B"));

    assertThat(security.getSecurityId()).isEqualTo(SecurityId.of("A", "B"));
    assertThat(security.getExpiryMonth()).isEqualTo(YearMonth.of(2015, 6));
    assertThat(security.getContractSpecId()).isEqualTo(FUTURE_CONTRACT.getId());
    assertThat(security.getExpiryDateCode()).isEmpty();
    assertThat(security.getInfo().getPriceInfo()).isEqualTo(FUTURE_CONTRACT.getPriceInfo());
  }

  public void createFutureFromOptionContractSpec() {
    assertThatThrownBy(() -> OPTION_CONTRACT.createFuture(YearMonth.of(2015, 6), ""))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Cannot create a Future from a contract specification of type 'Option'");
  }

  //-------------------------------------------------------------------------
  public void createOptionAutoId() {
    EtdOptionSecurity security = OPTION_CONTRACT.createOption(YearMonth.of(2015, 6), "", PutCall.CALL, 123.45);

    assertThat(security.getSecurityId()).isEqualTo(SecurityId.of(EtdIdUtils.ETD_SCHEME, "O-IFEN-BAR-201506-C123.45"));
    assertThat(security.getExpiryMonth()).isEqualTo(YearMonth.of(2015, 6));
    assertThat(security.getContractSpecId()).isEqualTo(OPTION_CONTRACT.getId());
    assertThat(security.getExpiryDateCode()).isEmpty();
    assertThat(security.getPutCall()).isEqualTo(PutCall.CALL);
    assertThat(security.getStrikePrice()).isEqualTo(123.45);
    assertThat(security.getInfo().getPriceInfo()).isEqualTo(OPTION_CONTRACT.getPriceInfo());
  }

  public void createOptionNonStandardAutoId() {
    EtdOptionSecurity security = OPTION_CONTRACT.createOption(YearMonth.of(2015, 6), "W1", PutCall.CALL, 123.45);

    assertThat(security.getSecurityId()).isEqualTo(SecurityId.of(EtdIdUtils.ETD_SCHEME, "O-IFEN-BAR-201506W1-C123.45"));
    assertThat(security.getExpiryMonth()).isEqualTo(YearMonth.of(2015, 6));
    assertThat(security.getContractSpecId()).isEqualTo(OPTION_CONTRACT.getId());
    assertThat(security.getExpiryDateCode()).isEqualTo("W1");
    assertThat(security.getPutCall()).isEqualTo(PutCall.CALL);
    assertThat(security.getStrikePrice()).isEqualTo(123.45);
    assertThat(security.getInfo().getPriceInfo()).isEqualTo(OPTION_CONTRACT.getPriceInfo());
  }

  public void createOptionManualId() {
    EtdOptionSecurity security =
        OPTION_CONTRACT.createOption(YearMonth.of(2015, 6), "", PutCall.CALL, 123.45, SecurityId.of("A", "B"));

    assertThat(security.getSecurityId()).isEqualTo(SecurityId.of("A", "B"));
    assertThat(security.getExpiryMonth()).isEqualTo(YearMonth.of(2015, 6));
    assertThat(security.getContractSpecId()).isEqualTo(OPTION_CONTRACT.getId());
    assertThat(security.getExpiryDateCode()).isEmpty();
    assertThat(security.getPutCall()).isEqualTo(PutCall.CALL);
    assertThat(security.getStrikePrice()).isEqualTo(123.45);
    assertThat(security.getInfo().getPriceInfo()).isEqualTo(OPTION_CONTRACT.getPriceInfo());
  }

  public void createOptionFromFutureContractSpec() {
    assertThatThrownBy(
        () -> FUTURE_CONTRACT.createOption(YearMonth.of(2015, 6), "", PutCall.CALL, 123.45))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Cannot create an Option from a contract specification of type 'Future'");
  }

  //-------------------------------------------------------------------------
  public void coverage() {
    coverImmutableBean(sut());
    coverBeanEquals(sut(), sut2());
  }

  public void test_serialization() {
    assertSerialization(sut());
  }

  //-------------------------------------------------------------------------
  static EtdContractSpec sut() {
    return EtdContractSpec.builder()
        .id(EtdContractSpecId.of("test", "123"))
        .type(EtdType.FUTURE)
        .exchangeId(ExchangeIds.ECAG)
        .contractCode("FOO")
        .description("A test future template")
        .priceInfo(SecurityPriceInfo.of(Currency.GBP, 100))
        .build();
  }

  static EtdContractSpec sut2() {
    return EtdContractSpec.builder()
        .id(EtdContractSpecId.of("test", "234"))
        .type(EtdType.OPTION)
        .exchangeId(ExchangeIds.IFEN)
        .contractCode("BAR")
        .description("A test option template")
        .priceInfo(SecurityPriceInfo.of(Currency.EUR, 10))
        .build();
  }

}
