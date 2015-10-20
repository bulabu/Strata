/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.math.impl.minimization;

import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.math.impl.FunctionUtils;
import com.opengamma.strata.math.impl.function.Function1D;

/**
 * 
 */
public abstract class MinimizationTestFunctions {
  public static final Function1D<DoubleArray, Double> ROSENBROCK = new Function1D<DoubleArray, Double>() {

    @Override
    public Double evaluate(DoubleArray x) {
      return FunctionUtils.square(1 - x.get(0)) + 100 * FunctionUtils.square(x.get(1) - FunctionUtils.square(x.get(0)));
    }
  };

  public static final Function1D<DoubleArray, DoubleArray> ROSENBROCK_GRAD =
      new Function1D<DoubleArray, DoubleArray>() {
        @Override
        public DoubleArray evaluate(DoubleArray x) {
          return DoubleArray.of(
              2 * (x.get(0) - 1) + 400 * x.get(0) * (FunctionUtils.square(x.get(0)) - x.get(1)),
              200 * (x.get(1) - FunctionUtils.square(x.get(0))));
        }
      };

  public static final Function1D<DoubleArray, Double> UNCOUPLED_ROSENBROCK = new Function1D<DoubleArray, Double>() {

    @Override
    public Double evaluate(final DoubleArray x) {
      final int n = x.size();
      if (n % 2 != 0) {
        throw new IllegalArgumentException("vector length must be even");
      }
      double sum = 0;
      for (int i = 0; i < n / 2; i++) {
        sum += FunctionUtils.square(1 - x.get(2 * i)) + 100 * FunctionUtils.square(x.get(2 * i + 1) - FunctionUtils.square(x.get(2 * i)));
      }
      return sum;
    }
  };

  public static final Function1D<DoubleArray, Double> COUPLED_ROSENBROCK = new Function1D<DoubleArray, Double>() {

    @Override
    public Double evaluate(DoubleArray x) {
      int n = x.size();

      double sum = 0;
      for (int i = 0; i < n - 1; i++) {
        sum += FunctionUtils.square(1 - x.get(i)) + 100 * FunctionUtils.square(x.get(i + 1) - FunctionUtils.square(x.get(i)));
      }
      return sum;
    }
  };

  public static final Function1D<DoubleArray, DoubleArray> COUPLED_ROSENBROCK_GRAD = new Function1D<DoubleArray, DoubleArray>() {

    @Override
    public DoubleArray evaluate(DoubleArray x) {
      int n = x.size();

      double[] res = new double[n];
      res[0] = 2 * (x.get(0) - 1) + 400 * x.get(0) * (FunctionUtils.square(x.get(0)) - x.get(1));
      res[n - 1] = 200 * (x.get(n - 1) - FunctionUtils.square(x.get(n - 2)));
      for (int i = 1; i < n - 1; i++) {
        res[i] = 2 * (x.get(i) - 1) + 400 * x.get(i) * (FunctionUtils.square(x.get(i)) - x.get(i + 1)) + 200
            * (x.get(i) - FunctionUtils.square(x.get(i - 1)));
      }
          return DoubleArray.copyOf(res);
    }
  };
}
