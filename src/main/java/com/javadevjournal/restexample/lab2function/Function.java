package com.javadevjournal.restexample.lab2function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Function {

	Sin sin;
	Cos cos;
	Csc csc;
	Sec sec;
	Cot cot;
	Log log;
	private static final double LOCAL_INF = 1E13;


	public Function() {
		this.sin = new Sin();
		this.cos = new Cos(sin);
		this.csc = new Csc(sin);
		this.sec = new Sec(cos);
		this.cot = new Cot(cos, sin);
		this.log = new Log();
	}

	public Function(Sin sin, Cos cos, Csc csc, Sec sec, Cot cot, Log log) {
		this.sin = sin;
		this.cos = cos;
		this.csc = csc;
		this.sec = sec;
		this.cot = cot;
		this.log = log;
	}

	public double SystemSolve(double x, double eps) {
		double res;
		if (x <= 0) {
			res = (
							(
									(
											(Math.pow((sec.sec(x, eps) - cot.cot(x, eps)), 2)) + cot.cot(x, eps))
											/ sin.sin(x, eps)) * (
									(csc.csc(x, eps) + Math.pow(cot.cot(x, eps), 3))
											/ (cot.cot(x, eps)
											/ (cos.cos(x, eps)
											/ (cos.cos(x, eps)
											* sin.sin(x, eps))))
							)
					);
		} else {
			res = Math.pow((Math.pow(Math.pow((log.log(10, x, eps)
					/ log.log(2, x, eps)), 3), 3)
					+ log.log(2, x, eps)), 3);
		}
		if (res > LOCAL_INF) {
			return Double.NaN;
		}
		return res;
	}

	public double writeResultToCSV(double x, double eps, Writer out) {
		double res = SystemSolve(x, eps);
		try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
			printer.printRecord(x, res);
		} catch (IOException e) {
			System.out.println("Wrong filename");
		}
		return res;
	}
}
