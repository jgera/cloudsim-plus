/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.utilizationmodels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implements a model, according to which a Cloudlet generates
 * random resource utilization every time frame.
 *
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 2.0
 */
public class UtilizationModelStochastic implements UtilizationModel {

	/** The random generator. */
	private Random randomGenerator;

	/** The utilization history map, where each key is a time and
         * each value is the utilization percentage in that time. */
	private Map<Double, Double> history;

	/**
	 * Instantiates a new utilization model stochastic.
	 */
	public UtilizationModelStochastic() {
		setHistory(new HashMap<>());
		setRandomGenerator(new Random());
	}

	/**
	 * Instantiates a new utilization model stochastic.
	 *
	 * @param seed the seed
	 */
	public UtilizationModelStochastic(long seed) {
		setHistory(new HashMap<>());
		setRandomGenerator(new Random(seed));
	}

	@Override
	public double getUtilization(double time) {
		if (getHistory().containsKey(time)) {
			return getHistory().get(time);
		}

		double utilization = getRandomGenerator().nextDouble();
		getHistory().put(time, utilization);
		return utilization;
	}

	/**
	 * Gets the utilization history.
	 *
	 * @return the history
	 */
	protected Map<Double, Double> getHistory() {
		return history;
	}

	/**
	 * Sets the utilization history.
	 *
	 * @param history the history
	 */
	protected final void setHistory(Map<Double, Double> history) {
		this.history = history;
	}

	/**
	 * Save the utilization history to a file.
	 *
	 * @param filename the filename
	 * @throws Exception the exception
	 */
	public void saveHistory(String filename) throws Exception {
		FileOutputStream fos = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(getHistory());
		oos.close();
	}

	/**
	 * Load an utilization history from a file.
	 *
	 * @param filename the filename
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public void loadHistory(String filename) throws Exception {
            FileInputStream fis = new FileInputStream(filename);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                setHistory((Map<Double, Double>) ois.readObject());
            }
	}

	/**
	 * Sets the random generator.
	 *
	 * @param randomGenerator the new random generator
	 */
	public final void setRandomGenerator(Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	/**
	 * Gets the random generator.
	 *
	 * @return the random generator
	 */
	public Random getRandomGenerator() {
		return randomGenerator;
	}

}