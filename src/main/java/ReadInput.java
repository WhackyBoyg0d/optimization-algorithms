package main.java;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ReadInput {
    public Map<String, Object> data;

    /**
     * Constructor for ReadInput
     */
    public ReadInput() {
        data = new HashMap<String, Object>();
    }

    //Genetic algorithm

    public float geneticAlgorithm() {
        @SuppressWarnings("unchecked")
        List<List<Integer>> solution = (List<List<Integer>>) data.get("solution");
        List<List<List<Integer>>> population = new ArrayList <List<List<Integer>>>();
        int populationSize = 100;
        for(int i = 0; i<populationSize; i++) {
            List<List<Integer>> newSolution = new ArrayList<List<Integer>>();
            for(int j = 0; j<solution.size(); j++) {
                List<Integer> cache = new ArrayList<Integer>();
                for(int k = 0; k<solution.get(j).size(); k++) {
                    cache.add(0);
                }
                newSolution.add(cache);
            }
            for(int j = 0; j<solution.size(); j++) {
                for(int k = 0; k<solution.get(j).size(); k++) {
                    newSolution.get(j).set(k, solution.get(j).get(k));
                }
            }
            for(int j = 0; j<solution.size(); j++) {
                for(int k = 0; k<solution.get(j).size(); k++) {
                    Random rand = new Random();
                    int random = rand.nextInt(2);
                    if(random == 1) {
                        List<List<Integer>> testSolution = new ArrayList<List<Integer>>();
                        for(int l = 0; l<solution.size(); l++) {
                            List<Integer> cache = new ArrayList<Integer>();
                            for(int m = 0; m<solution.get(l).size(); m++) {
                                cache.add(0);
                            }
                            testSolution.add(cache);
                        }
                        for(int l = 0; l<newSolution.size(); l++) {
                            for(int m = 0; m<newSolution.get(l).size(); m++) {
                                testSolution.get(l).set(m, newSolution.get(l).get(m));
                            }
                        }
                        testSolution.get(j).set(k, 1 - testSolution.get(j).get(k));
                        if(checkSize(testSolution)) {
                            newSolution.get(j).set(k, 1 - newSolution.get(j).get(k));
                        }
                    }
                }
            } 
            // System.out.println("Fitness: " + fitnessFunction(newSolution));
            population.add(newSolution);
        }
        Collections.sort(population, new Comparator<List<List<Integer>>>() {
            @Override
            public int compare(List<List<Integer>> o1, List<List<Integer>> o2) {
                return Double.compare(fitnessFunction(o2), fitnessFunction(o1));
            }
        });
        int generations = 150;
        for(int ii = 0; ii<generations; ii++){
        //Crossover
            int crossoverRate = 90;
            for(int i = 0; i < populationSize; i += 2){
                Random rand = new Random();
                int random = rand.nextInt(100);
                if(random < crossoverRate){
                    List<List<Integer>> parent1 = population.get(i);
                    List<List<Integer>> parent2 = population.get(i+1);
                    List<List<Integer>> child1 = new ArrayList<List<Integer>>();
                    List<List<Integer>> child2 = new ArrayList<List<Integer>>();

                    int crossoverPoint = parent1.size()/2;
                    for (int j = 0; j < parent1.size(); j++) {
                        List<Integer> gene1 = new ArrayList<>(parent1.get(j));
                        List<Integer> gene2 = new ArrayList<>(parent2.get(j));
                        
                        if (j < crossoverPoint) {
                            child1.add(gene1);
                            child2.add(gene2);
                        } else {
                            child1.add(gene2);
                            child2.add(gene1);
                        }
                    }
                    population.add(child1);
                    // System.out.println("Child 1: " + child1);
                    // System.out.println("FitnessChild: " + fitnessFunction(child1));
                    population.add(child2);
                    // System.out.println("Child 2: " + child2);
                    // System.out.println("FitnessChild: " + fitnessFunction(child2));
                }
            }
            //Mutation
            int mutationRate = 3;
            for(int i = 0; i < population.size(); i++){
                List<List<Integer>> member = population.get(i);
                for(int j = 0; j < member.size(); j++){
                    for(int k = 0; k < member.get(j).size(); k++){
                        Random rand = new Random();
                        int random = rand.nextInt(100);
                        if(random < mutationRate){
                            List<List<Integer>> testMember = new ArrayList<List<Integer>>();
                            for(int l = 0; l < member.size(); l++){
                                List<Integer> cache = new ArrayList<Integer>();
                                for(int m = 0; m < member.get(l).size(); m++){
                                    cache.add(0);
                                }
                                testMember.add(cache);
                            }
                            for(int l = 0; l < member.size(); l++){
                                for(int m = 0; m < member.get(l).size(); m++){
                                    testMember.get(l).set(m, member.get(l).get(m));
                                }
                            }
                            testMember.get(j).set(k, 1 - testMember.get(j).get(k));
                            if(checkSize(testMember)){
                                member.get(j).set(k, 1 - member.get(j).get(k));
                            }
                        }
                    }
                }
            }
            //Selection
            Collections.sort(population, new Comparator<List<List<Integer>>>() {
                @Override
                public int compare(List<List<Integer>> o1, List<List<Integer>> o2) {
                    return Double.compare(fitnessFunction(o2), fitnessFunction(o1));
                }
            });
            // System.out.println("Population size: " + population.size()) ;
            // population = population.subList(0, 100);
            List<List<List<Integer>>> firstHalf = new ArrayList<List<List<Integer>>>();
            firstHalf = population.subList(0, 95);
            List<List<List<Integer>>> secondHalf = new ArrayList<List<List<Integer>>>();
            int i = population.size() - 1;
            int j = 0;
            while(j < 5){
                secondHalf.add(population.get(i));
                i--;
                j++;
            }
            population = new ArrayList<List<List<Integer>>>();
            population.addAll(firstHalf);
            population.addAll(secondHalf);
            Collections.shuffle(population);

            // System.out.println("Population size 2: " + population.size()) ;
            // for(int l = 0; l < population.size(); l++){
            //     System.out.println("Fitness: " + fitnessFunction(population.get(l)));
            // }
        }
        System.out.println("Solution by Genetic Algorithm") ;
        System.out.println("Best fitness: " + fitnessFunction(population.get(0)));
        // System.out.println("Best solution: " + population.get(0));
        solution = population.get(0);
        System.out.println("Best Solution: " + solution);
        return fitnessFunction(population.get(0));
    }

    public float fitnessFunction(List<List<Integer>> solution) {
        float reqTotal = 0;
        float num = 0;
        @SuppressWarnings("unchecked")
        Map<String, String> video_ed_request = (Map<String, String>) data.get("video_ed_request");
        @SuppressWarnings("unchecked")
        List<Integer> ep_to_dc_latency = (List<Integer>) data.get("ep_to_dc_latency");
        @SuppressWarnings("unchecked")
        List<List<Integer>> ep_to_cache_latency = (List<List<Integer>>) data.get("ep_to_cache_latency");
        @SuppressWarnings("unchecked")
        List<List<Integer>> ed_cache_list = (List<List<Integer>>) data.get("ed_cache_list");
        if(checkSize(solution) == false) {
            return 0;
        }
        for (String video_ed  : video_ed_request.keySet())
        {
            int req = Integer.parseInt(video_ed_request.get(video_ed));
            reqTotal += req;
            String[] video_ed_arr = video_ed.split(",");
            int videoId = Integer.parseInt(video_ed_arr[0]);
            int edId = Integer.parseInt(video_ed_arr[1]);
            int latency_dc = ep_to_dc_latency.get(edId);
            int latency_cache = 0;
            int cacheId = -1;
            if(ed_cache_list.get(edId).size() > 0) {
                for(int i = 0; i < ed_cache_list.get(edId).size(); i++) {
                    int cache = ed_cache_list.get(edId).get(i);
                    if(solution.get(cache).get(videoId) == 1) {
                        if(latency_cache == 0) {
                            latency_cache = ep_to_cache_latency.get(edId).get(cache);
                            cacheId = cache;
                        } else {
                            if(ep_to_cache_latency.get(edId).get(cache) < latency_cache) {
                                latency_cache = ep_to_cache_latency.get(edId).get(cache);
                                cacheId = cache;
                            }
                        }
                    }
                }
            }
            if(cacheId != -1) {
                num += req * (latency_dc - latency_cache);
            }    
        }
        
        return (int) num / reqTotal * 1000;
    }

    public boolean checkSize(List<List<Integer>> solution) {
        int number_of_videos = (Integer) data.get("number_of_videos");
        int number_of_caches = (Integer) data.get("number_of_caches");
        int cache_size = (Integer) data.get("cache_size");
        int[] video_size_desc = (int[]) data.get("video_size_desc");
        for(int i = 0; i<number_of_caches; i++) {
            int currentCapacity = cache_size;
            for(int j = 0; j<number_of_videos; j++) {
                if(solution.get(i).get(j) == 1) {
                    currentCapacity = currentCapacity - video_size_desc[j];
                }
            }
            if(currentCapacity < 0) {
                return false;
            }
        }
        return true;
    }

    public List<List<Integer>> hillClimbingStep() {
        int number_of_videos = (Integer) data.get("number_of_videos");
        int number_of_caches = (Integer) data.get("number_of_caches");
        @SuppressWarnings("unchecked")
        List<List<Integer>> solution = (List<List<Integer>>) data.get("solution");
        float currentFitness = fitnessFunction(solution);
        List<List<Integer>> bestSolution = new ArrayList<List<Integer>>();
        for(int i = 0; i<number_of_caches; i++) {
            List<Integer> cache = new ArrayList<Integer>();
            for(int j = 0; j<number_of_videos; j++) {
                cache.add(0);
            }
            bestSolution.add(cache);
        }
        for(int i = 0; i<number_of_caches; i++) {
            for(int j = 0; j<number_of_videos; j++) {
                bestSolution.get(i).set(j, solution.get(i).get(j));
            }
        }
        // System.out.println("Start");
        float bestFitness = currentFitness;
        boolean improved = true;
        int ctr = 0;
        while (improved) {
            improved = false;
            for (int i = 0; i < solution.size(); i++) {
                for (int j = 0; j < solution.get(i).size(); j++) {
                    // System.out.println("i: " + i + " j: " + j);
                    List<List<Integer>> newSolution = new ArrayList<List<Integer>>();
                    for(int k = 0; k<number_of_caches; k++) {
                        List<Integer> cache = new ArrayList<Integer>();
                        for(int l = 0; l<number_of_videos; l++) {
                            cache.add(0);
                        }
                        newSolution.add(cache);
                    }
                    for(int k = 0; k<number_of_caches; k++) {
                        for(int l = 0; l<number_of_videos; l++) {
                            newSolution.get(k).set(l, solution.get(k).get(l));
                        }
                    }
                    newSolution.get(i).set(j, 1 - newSolution.get(i).get(j));
                    // System.out.println("New solution: " + newSolution);
                    float newFitness = fitnessFunction(newSolution);
                    // System.out.println("New fitness: " + newFitness);
                    if (newFitness > bestFitness && checkSize(newSolution)) {
                        ctr = 0;
                        for(int k = 0; k<number_of_caches; k++) {
                            for(int l = 0; l<number_of_videos; l++) {
                                bestSolution.get(k).set(l, newSolution.get(k).get(l));
                            }
                        }
                        bestFitness = newFitness;
                        improved = true;
                        // System.out.println("New best fitness: " + bestFitness);
                        // System.out.println("New best solution: " + bestSolution);
                    }
                    ctr++;
                // System.out.println("Ctr: " + ctr); 
            }
            
            }
        }
        // System.out.println("Best fitness: " + bestFitness);
        return bestSolution;
    }

    public void hillClimbing(){
        boolean improving = true;
        int counter = 0;
        while(improving) {
            List<List<Integer>> newSolution = hillClimbingStep();
            if(newSolution.equals(data.get("solution"))) {
                improving = false;
            }
            data.put("solution", newSolution);
            counter++;
            // System.out.println("Counter: " + counter);
        }
        //Random restart
        if(counter > 1000) {
            @SuppressWarnings("unchecked")
            List<List<Integer>> solution = (List<List<Integer>>) data.get("solution");
            for(int i = 0; i<solution.size(); i++) {
                for(int j = 0; j<solution.get(i).size(); j++) {
                    Random rand = new Random();
                    int random = rand.nextInt(2);
                    solution.get(i).set(j, random);
                }
            }
            data.put("solution", solution);
            hillClimbing();
        }
        System.out.println("Solution by HillClimbing" );
        @SuppressWarnings("unchecked")
        List<List<Integer>> solution = (List<List<Integer>>) data.get("solution");
        System.out.println("Final fitness: " + fitnessFunction(solution));
        System.out.println("Final solution: " + data.get("solution"));
    }

    public void initSol(){
        int number_of_videos = (Integer) data.get("number_of_videos");
        int number_of_caches = (Integer) data.get("number_of_caches");
        List<List<Integer>> solution = new ArrayList<List<Integer>>();
        for(int i = 0; i<number_of_caches; i++) {
            List<Integer> cache = new ArrayList<Integer>();
            for(int j = 0; j<number_of_videos; j++) {
                cache.add(0);
            }
            solution.add(cache);
        }
        data.put("solution", solution);
    }

    public void fitness() {
        //TODO: Your code here
        int number_of_videos = (Integer) data.get("number_of_videos");
        int number_of_endpoints = (Integer) data.get("number_of_endpoints");
        int number_of_requests = (Integer) data.get("number_of_requests");
        int number_of_caches = (Integer) data.get("number_of_caches");
        int cache_size = (Integer) data.get("cache_size");
        int[] video_size_desc = (int[]) data.get("video_size_desc");
        @SuppressWarnings("unchecked")
        List<Integer> ep_to_dc_latency = (List<Integer>) data.get("ep_to_dc_latency");
        @SuppressWarnings("unchecked")
        List<List<Integer>> ep_to_cache_latency = (List<List<Integer>>) data.get("ep_to_cache_latency");
        @SuppressWarnings("unchecked")
        List<List<Integer>> ed_cache_list = (List<List<Integer>>) data.get("ed_cache_list");
        @SuppressWarnings("unchecked")
        Map<String, String> video_ed_request = (Map<String, String>) data.get("video_ed_request");

        //create solution
        initSol();
        @SuppressWarnings("unchecked")
        List<List<Integer>> solution = (List<List<Integer>>) data.get("solution");

        data.put("solution", solution);
        int min = video_size_desc[0]; // Assume first element is smallest
        for (int i = 1; i < number_of_videos; i++) {
            if (video_size_desc[i] < min) {
                min = video_size_desc[i]; // Update min if current element is smaller
            }
        }
        System.err.println("Solution Created");


        
        System.out.println(fitnessFunction(solution));
        hillClimbing();
        @SuppressWarnings("unchecked")
        List<List<Integer>> solutionHillClimbing = (List<List<Integer>>) data.get("solution");
        System.out.println(fitnessFunction(solutionHillClimbing));

        initSol();
        geneticAlgorithm();

        
    }

    public void readGoogle(String filename) throws IOException {

        BufferedReader fin = new BufferedReader(new FileReader(filename));
    
        String system_desc = fin.readLine();
        String[] system_desc_arr = system_desc.split(" ");
        int number_of_videos = Integer.parseInt(system_desc_arr[0]);
        int number_of_endpoints = Integer.parseInt(system_desc_arr[1]);
        int number_of_requests = Integer.parseInt(system_desc_arr[2]);
        int number_of_caches = Integer.parseInt(system_desc_arr[3]);
        int cache_size = Integer.parseInt(system_desc_arr[4]);
    
        Map<String, String> video_ed_request = new HashMap<String, String>();
        String video_size_desc_str = fin.readLine();
        String[] video_size_desc_arr = video_size_desc_str.split(" ");
        int[] video_size_desc = new int[video_size_desc_arr.length];
        for (int i = 0; i < video_size_desc_arr.length; i++) {
            video_size_desc[i] = Integer.parseInt(video_size_desc_arr[i]);
        }
    
        List<List<Integer>> ed_cache_list = new ArrayList<List<Integer>>();
        List<Integer> ep_to_dc_latency = new ArrayList<Integer>();
        List<List<Integer>> ep_to_cache_latency = new ArrayList<List<Integer>>();
        for (int i = 0; i < number_of_endpoints; i++) {
            ep_to_dc_latency.add(0);
            ep_to_cache_latency.add(new ArrayList<Integer>());
    
            String[] endpoint_desc_arr = fin.readLine().split(" ");
            int dc_latency = Integer.parseInt(endpoint_desc_arr[0]);
            int number_of_cache_i = Integer.parseInt(endpoint_desc_arr[1]);
            ep_to_dc_latency.set(i, dc_latency);
    
            for (int j = 0; j < number_of_caches; j++) {
                ep_to_cache_latency.get(i).add(ep_to_dc_latency.get(i) + 1);
            }
    
            List<Integer> cache_list = new ArrayList<Integer>();
            for (int j = 0; j < number_of_cache_i; j++) {
                String[] cache_desc_arr = fin.readLine().split(" ");
                int cache_id = Integer.parseInt(cache_desc_arr[0]);
                int latency = Integer.parseInt(cache_desc_arr[1]);
                cache_list.add(cache_id);
                ep_to_cache_latency.get(i).set(cache_id, latency);
            }
            ed_cache_list.add(cache_list);
        }
    
        for (int i = 0; i < number_of_requests; i++) {
            String[] request_desc_arr = fin.readLine().split(" ");
            String video_id = request_desc_arr[0];
            String ed_id = request_desc_arr[1];
            String requests = request_desc_arr[2];
            video_ed_request.put(video_id + "," + ed_id, requests);
        }
    
        data.put("number_of_videos", number_of_videos);
        data.put("number_of_endpoints", number_of_endpoints);
        data.put("number_of_requests", number_of_requests);
        data.put("number_of_caches", number_of_caches);
        data.put("cache_size", cache_size);
        data.put("video_size_desc", video_size_desc);
        data.put("ep_to_dc_latency", ep_to_dc_latency);
        data.put("ep_to_cache_latency", ep_to_cache_latency);
        data.put("ed_cache_list", ed_cache_list);
        data.put("video_ed_request", video_ed_request);
    
        fin.close();
     
     }

     public String toString() {
        String result = "";

        //for each endpoint: 
        for(int i = 0; i < (Integer) data.get("number_of_endpoints"); i++) {
            result += "enpoint number " + i + "\n";
            //latendcy to DC
            @SuppressWarnings("unchecked")
            int latency_dc = ((List<Integer>) data.get("ep_to_dc_latency")).get(i);
            result += "latency to dc " + latency_dc + "\n";
            //for each cache
            @SuppressWarnings("unchecked")
            List<List<Integer>> ep_to_cache_latency = (List<List<Integer>>) data.get("ep_to_cache_latency");
            for(int j = 0; j < ep_to_cache_latency.get(i).size(); j++) {
                @SuppressWarnings("unchecked")
                int latency_c = ((List<List<Integer>>) data.get("ep_to_cache_latency")).get(i).get(j); 
                result += "latency to cache number " + j + " = " + latency_c + "\n";
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {  
        ReadInput ri = new ReadInput();
        ri.readGoogle("input/me_at_the_zoo.in");
        System.out.println(ri.data.get("video_ed_request"));
        System.out.println(ri.toString());
        ri.fitness();
    }
}
