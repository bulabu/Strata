/*
 * Copyright (C) 2017 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.etd;

import static com.opengamma.strata.collect.TestHelper.coverPrivateConstructor;
import static org.testng.Assert.assertEquals;

import java.time.YearMonth;

import org.testng.annotations.Test;

import com.opengamma.strata.basics.StandardId;
import com.opengamma.strata.product.SecurityId;
import com.opengamma.strata.product.common.ExchangeIds;
import com.opengamma.strata.product.common.PutCall;

/**
 * Test {@link EtdIdUtils}.
 */
@Test
public class EtdIdUtilsTest {

  public void test_contractSpecId_future() {
    EtdContractSpecId test = EtdIdUtils.contractSpecId(EtdType.FUTURE, ExchangeIds.ECAG, "FGBS");
    assertEquals(test.getStandardId(), StandardId.of("OG-ETD", "F-ECAG-FGBS"));
  }

  public void test_contractSpecId_option() {
    EtdContractSpecId test = EtdIdUtils.contractSpecId(EtdType.OPTION, ExchangeIds.ECAG, "OGBS");
    assertEquals(test.getStandardId(), StandardId.of("OG-ETD", "O-ECAG-OGBS"));
  }

  public void test_futureId_simple() {
    SecurityId test = EtdIdUtils.futureId(ExchangeIds.ECAG, "FGBS", YearMonth.of(2017, 6), "");
    assertEquals(test.getStandardId(), StandardId.of("OG-ETD", "F-ECAG-FGBS-201706"));
  }

  public void test_futureId_complex() {
    SecurityId test = EtdIdUtils.futureId(ExchangeIds.ECAG, "FGBS", YearMonth.of(2017, 6), "W2");
    assertEquals(test.getStandardId(), StandardId.of("OG-ETD", "F-ECAG-FGBS-201706W2"));
  }

  public void test_optionId_simple() {
    SecurityId test = EtdIdUtils.optionId(ExchangeIds.ECAG, "FGBS", YearMonth.of(2017, 6), "", PutCall.PUT, 12.34);
    assertEquals(test.getStandardId(), StandardId.of("OG-ETD", "O-ECAG-FGBS-201706-P12.34"));
  }

  public void test_optionId_complex() {
    SecurityId test = EtdIdUtils.optionId(ExchangeIds.ECAG, "FGBS", YearMonth.of(2017, 6), "W2", PutCall.PUT, 12.34);
    assertEquals(test.getStandardId(), StandardId.of("OG-ETD", "O-ECAG-FGBS-201706W2-P12.34"));
  }

  //-------------------------------------------------------------------------
  public void test_coverage() {
    coverPrivateConstructor(EtdIdUtils.class);
  }

}
