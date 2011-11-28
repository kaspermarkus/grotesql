(ns grotesql.output)
(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io])

(defn stdout
	"Prints table to standard output"
	[ table ]
	(do
	   (println (apply str (interpose ", " (map name (keys (first table))))))
	   (println (apply str (interpose "\n" (map (fn [row] (apply str (interpose ", " (vals row)))) table))))))

(defn output-csv
	"Outputs to csv file separated by separator. If header is true, keys are used for header in first row"
	[ filename separator header? table ]
	(let [  data-body (map (fn [row] (vec (vals row))) table) 
		out-data (if header? 
			(cons (vec (map name (keys (first table)))) data-body) 
			data-body)]
		(with-open [out-file (io/writer filename)] (csv/write-csv out-file out-data :separator separator))))
		
