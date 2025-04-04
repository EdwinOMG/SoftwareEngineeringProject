package project.annotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.ComputationHandler;
import main.java.ComputationHandlerImpl;
import main.java.ComputeEngine;
import main.java.DataStore;
import main.java.DigitChains;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;

public class TestMultiUser {

	// TODO 1: change the type of this variable to the name you're using for your @NetworkAPI
	// interface
	private ComputationHandler coordinator;
	private ComputeEngine mockEngine;
	private DataStore mockStore;

	@BeforeEach
	public void initializeComputeEngine() {

		//TODO 2: create an instance of the implementation of your @NetworkAPI; this is the component
		// that the user will make requests to
		// Store it in the 'coordinator' instance variable

		mockEngine = mock(ComputeEngine.class);
		mockStore = mock(DataStore.class);

		 when(mockStore.read(any(InputConfig.class)))
         .thenReturn(List.of(1, 15, 10, 5, 2, 3, 8));

     when(mockEngine.compute(any(Iterable.class)))
         .thenReturn(new DigitChains(List.of(
             List.of(1), 
             List.of(15, 26, 40, 16, 37, 58, 89),
             List.of(10, 1),
             List.of(5, 25, 29, 85, 89),
             List.of(2, 4, 16, 37, 58, 89),
             List.of(3, 9, 81, 65, 61, 37, 58, 89),
             List.of(8, 64, 52, 28, 85, 89)
         )));

     when(mockStore.appendResult(any(OutputConfig.class), any(DigitChains.class), anyChar()))
         .thenReturn(new OutputResult() {
             @Override
             public OutputResult.ShowResultStatus getStatus() {
                 return OutputResult.ShowResultStatus.SUCCESS;
             }
         });

     this.coordinator = new ComputationHandlerImpl(mockEngine, mockStore);
 }

	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int nThreads = 4;
		List<TestUser> testUsers = new ArrayList<>();
		for (int i = 0; i < nThreads; i++) {
			testUsers.add(new TestUser(coordinator));
		}

		// Run single threaded
		String singleThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.singleThreadOut.tmp";
		for (int i = 0; i < nThreads; i++) {
			File singleThreadedOut = 
					new File(singleThreadFilePrefix + i);
			singleThreadedOut.deleteOnExit();
			testUsers.get(i).run(singleThreadedOut.getCanonicalPath());
		}

		// Run multi threaded
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<?>> results = new ArrayList<>();
		String multiThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.multiThreadOut.tmp";
		for (int i = 0; i < nThreads; i++) {
			File multiThreadedOut = 
					new File(multiThreadFilePrefix + i);
			multiThreadedOut.deleteOnExit();
			String multiThreadOutputPath = multiThreadedOut.getCanonicalPath();
			TestUser testUser = testUsers.get(i);
			results.add(threadPool.submit(() -> testUser.run(multiThreadOutputPath)));
		}

		results.forEach(future -> {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});


		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix, nThreads);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix, nThreads);
		Assert.assertEquals(singleThreaded, multiThreaded);
	}

	private List<String> loadAllOutput(String prefix, int nThreads) throws IOException {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < nThreads; i++) {
			File multiThreadedOut = 
					new File(prefix + i);
			result.addAll(Files.readAllLines(multiThreadedOut.toPath()));
		}
		return result;
	}
}