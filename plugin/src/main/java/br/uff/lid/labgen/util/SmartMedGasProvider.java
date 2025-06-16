package br.uff.lid.labgen.util;

import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

public class SmartMedGasProvider extends DefaultGasProvider {
    private BigInteger gasPrice = BigInteger.valueOf(0);
    private BigInteger gasLimit = BigInteger.valueOf(4700000L);
    public SmartMedGasProvider() {
        super();
    }

    public SmartMedGasProvider(BigInteger gasPrice, BigInteger gasLimit) {
        super();
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
    }

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return this.gasPrice;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return this.gasLimit;
    }
}
