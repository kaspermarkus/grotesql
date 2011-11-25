(ns grotesql.input)
(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io])

(defn input-csv 
	"Reads csv file, separated by separator. If header is true, first line is considered header"
	[ filename separator header? ] 
	(let [  full-input (with-open [in-file (io/reader filename)] (doall (csv/read-csv in-file :separator separator)))
		data (if header? (rest full-input) full-input)
		headers (if header?
                        ;read first line of file and change values to keywords in a list
                        (map (fn [val] (keyword (clojure.string/trim val))) (first full-input)) 
                        ;else make up our own headers
                        (map (fn [ num ] (keyword (str "header" num))) (iterate inc 1)))]
                 (filter identity (map (fn [row] ;filter to ensure no nil values
			(if (not (and (= [""] row) (= (count row) 1))) ;ignore empty lines (make nil)
				(apply hash-map (interleave headers row)))) data))))
