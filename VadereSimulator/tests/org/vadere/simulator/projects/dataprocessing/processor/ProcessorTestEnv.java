package org.vadere.simulator.projects.dataprocessing.processor;

import org.vadere.simulator.control.SimulationState;
import org.vadere.simulator.projects.dataprocessing.ProcessorManager;
import org.vadere.simulator.projects.dataprocessing.VadereStringWriter;
import org.vadere.simulator.projects.dataprocessing.datakey.DataKey;
import org.vadere.simulator.projects.dataprocessing.outputfile.OutputFile;
import org.vadere.simulator.projects.dataprocessing.outputfile.OutputFileFactory;
import org.vadere.tests.reflection.ReflectionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;

/**
 * A {@link ProcessorTestEnv} encapsulates needed  dependencies to test a {@link DataProcessor}. If
 * possible dependencies are mocked via {@link org.mockito.Mockito}.
 *
 * @author Stefan Schuhbäck
 */
public abstract class ProcessorTestEnv<K extends DataKey<K>, V> {

	/**
	 * processor under test
	 */
	DataProcessor<?, ?> testedProcessor;
	/**
	 * List of {@link SimulationState}s used for test. (mocked)
	 */
	List<SimulationStateMock> states;
	/**
	 * Ids of {@link DataProcessor}s
	 */
	int nextProcessorId;
	/**
	 * Corresponding {@link OutputFile} needed by {@link #testedProcessor}
	 */
	OutputFile outputFile;
	Map<K, V> expectedOutput;
	/**
	 * Factories
	 */
	DataProcessorFactory processorFactory;
	OutputFileFactory outputFileFactory;
	/**
	 * Needed for DataProcessor doUpdate call. (mocked)
	 */
	private ProcessorManager manager;
	/**
	 * If {@link #testedProcessor} has dependencies to other {@link DataProcessor}s
	 */
	private Map<Integer, DataProcessor<?, ?>> requiredProcessors;
	private String delimiter;


	ProcessorTestEnv() {
		manager = mock(ProcessorManager.class);
		states = new ArrayList<>();
		nextProcessorId = 1;
		expectedOutput = new HashMap<>();
		new ArrayList<>();
		delimiter = " ";
		testedProcessor = null;
		outputFile = null;
		requiredProcessors = new HashMap<>();
		processorFactory = new DataProcessorFactory();
		outputFileFactory = new OutputFileFactory();
	}

	/**
	 * Initialize {@link DataProcessor} and {@link OutputFile}
	 */
	void init() {
		delimiter = outputFile.getSeparator();
		outputFile.init(getProcessorMap());
		testedProcessor.init(manager);
	}

	/**
	 * Overwrite to add {@link SimulationStateMock}s needed for test.
	 */
	public abstract void loadDefaultSimulationStateMocks();

	List<SimulationState> getSimStates() {
		return states.stream().map(s -> s.state).collect(Collectors.toList());
	}

	void addToExpectedOutput(K dataKey, V value) {
		expectedOutput.put(dataKey, value);
	}

	Map<K, V> getExpectedOutput() {
		return expectedOutput;
	}

	abstract List<String> getExpectedOutputAsList();

	ProcessorManager getManager() {
		return manager;
	}

	DataProcessor<?, ?> getTestedProcessor() {
		return testedProcessor;
	}

	String getDelimiter() {
		return delimiter;
	}


	private Map<Integer, DataProcessor<?, ?>> getProcessorMap() {
		Map<Integer, DataProcessor<?, ?>> processorMap = new LinkedHashMap<>();
		processorMap.put(testedProcessor.getId(), testedProcessor);
		if (requiredProcessors != null && requiredProcessors.size() > 0)
			processorMap.putAll(requiredProcessors);

		return processorMap;
	}

	OutputFile getOutputFile() {
		return outputFile;
	}

	List<String> getOutput() throws NoSuchFieldException, IllegalAccessException {
		ReflectionHelper r = ReflectionHelper.create(outputFile);
		VadereStringWriter writer = (VadereStringWriter) r.valOfField("writer");
		return writer.getOutput().subList(1, writer.getOutput().size());
	}

	String getHeader() throws NoSuchFieldException, IllegalAccessException {
		ReflectionHelper r = ReflectionHelper.create(outputFile);
		VadereStringWriter writer = (VadereStringWriter) r.valOfField("writer");
		return writer.getOutput().get(0);
	}

	int nextProcessorId() {
		return nextProcessorId++;
	}
}
