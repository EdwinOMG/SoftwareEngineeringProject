package project.annotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import main.java.server.DataStoreClient;


public class TestMultiUser {
    private ComputationHandler coordinator;
    private ComputeEngine mockEngine;
    private DataStoreClient mockStore;
    
    @BeforeEach
    public void initializeComputeEngine() throws IOException {
        mockEngine = mock(ComputeEngine.class);
        mockStore = mock(DataStoreClient.class);
        
        // Mock readFile
        try {
			when(mockStore.readFile(anyString()))
			    .thenReturn(List.of(1, 15, 10, 5, 2, 3, 8));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // Mock compute
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
        
        doNothing().when(mockStore).writeFile(anyString(), anyList(), anyString());
       
        
        this.coordinator = new ComputationHandlerImpl(mockEngine, mockStore);
    }

	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int threadCount = 4;
		List<TestUser> testUsers = new ArrayList<TestUser>();
		for (int i = 0; i < threadCount; i++) {
			testUsers.add(new TestUser(coordinator));
		}
		
		Path outputDir = Paths.get("test-output");
	    if (!Files.exists(outputDir)) {
	        Files.createDirectories(outputDir);
	    }
	    
		// Run single threaded
	    String singleThreadFilePrefix = outputDir.resolve("singleThreadOut.tmp").toString();
	    for (int i = 0; i < threadCount; i++) {
	        Path outputPath = Paths.get(singleThreadFilePrefix + i);
	        testUsers.get(i).run(outputPath.toString());
	    }
		
		// Run multi threaded
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<?>> results = new ArrayList<>();
		String multiThreadFilePrefix = outputDir.resolve("multiThreadOut.tmp").toString();

		for (int i = 0; i < threadCount; i++) {
	        Path outputPath = Paths.get(multiThreadFilePrefix + i);
	        TestUser testUser = testUsers.get(i);
	        results.add(threadPool.submit(() -> testUser.run(outputPath.toString())));
	    }
		
		results.forEach(future -> {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		
		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix, threadCount);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix, threadCount);
		Assert.assertEquals(singleThreaded, multiThreaded);
	}

	private List<String> loadAllOutput(String prefix, int threadCount) throws IOException {
	    List<String> result = new ArrayList<>();
	    for (int i = 0; i < threadCount; i++) {
	        Path filePath = Paths.get(prefix + i);
	        if (Files.exists(filePath)) {
	            result.addAll(Files.readAllLines(filePath));
	        }
	    }
	    return result;
	}

}
