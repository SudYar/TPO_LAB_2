package com.javadevjournal.restexample.lab2function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Cot {
	private final Cos cos;
	private final Sin sin;
	private static final double LOCAL_INF = 1E13;

	public Cot(Cos cos, Sin sin) {
		this.cos = cos;
		this.sin = sin;
	}

	public Cot() {
		this.cos = new Cos();
		this.sin = new Sin();
	}

	public double cot(double x, double eps) {
		double cosVal = cos.cos(x, eps);
		double sinVal = sin.sin(x, eps);
		if (Double.isNaN(cosVal) || Double.isNaN(sinVal) || sinVal == 0) return Double.NaN;
		double res = cosVal / sinVal;
		if (res > LOCAL_INF) return Double.NaN;
		return res;
	}

	public double writeResultToCSV(double x, double eps, Writer out) {
		double res = cot(x, eps);
		try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
			printer.printRecord(x, res);
		} catch (IOException e) {
			System.out.println("Wrong filename");
		}
		return res;
	}
}