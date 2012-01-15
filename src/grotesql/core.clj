(ns grotesql.core
(:use [clojure.pprint])
(:require 
		[clojure.data.csv :as csv]
    	[clojure.java.io :as io ] ))
		
;Private

(def  ^:dynamic *result_work* (ref '()))

(defn input-csv 
         "Reads csv file, separated by separator. If header is true, first line is considered header"
         ([ filename separator header? ]
           (let [  full-input (with-open [in-file (io/reader filename)] 
                                         (doall (csv/read-csv in-file :separator separator)))
                 data (if header? (rest full-input) full-input)
                 headers (if header?
                           ;read first line of file and change values to keywords in a list
                           (map (fn [val] (keyword (clojure.string/trim val))) (first full-input)) 
                           ;else make up our own headers
                           (map (fn [ num ] (keyword (str "header" num))) (iterate inc 1)))]
             (filter identity (map (fn [row] ;filter to ensure no nil values
                                     (if (not (and (= [""] row) (= (count row) 1))) ;ignore empty lines (make nil)
                                       (apply hash-map (interleave headers row)))) data))))
         ; Limit number of lines taken by supplying it as extra parameter
         ([filename separator header? numlines]
           (take numlines (input-csv filename separator header?))))

(defn drop-columns
    "Takes [keys] and a table and drops the columns that has the names of the keys"
    [ keys table ]
    (map (fn [row] (apply dissoc row keys )) table ))

(defn ignore-node? [node]
	"takes a node and test if the node is to be executed"
	(or (true? (:disabled? node)) (= :dropzone (:type node)) (empty? node)))



;Public

(comment defn got-key 
         	"Returns wither m(ap) got k(ey)" 	
         	[k m] (not (nil? (k m))))

(defn node-type
  	"Returns the :node-type for multimethod resolution"
  	[x] (get x :type))

(defmulti node node-type )	


(defmethod node :csv-in [node]
	(let [options (:options node) filename (:filename options) ch (:separator options) header (:header options) ]
	(input-csv filename ch header)))


(defmethod node :drop-columns [node]
	(let [options (:options node) k (:keys options) ]
	(drop-columns k @*result_work*  )))
			  
(defmethod node :print-node [x]
	(str x))			  	

(defmethod node :dropzone [_] nil)
(defmethod node :default [node] {:error "Not implemented" :node (str node) } )

(defmethod node :no-match [_] (println {:error "Not implemented (is :type defined?)"}) )

(def test-node [{:type :csv-in :test1 1 :test2 2} {:type :drop-columns :test1 11 :test2 22}] )

(defn clean-pipe [pipe]
	"cleans the pipe, for unused nodes (:dropzones) and :disabled? nodes returns only nodes to be evaluated"
	(filter #(not (ignore-node? %)) pipe))

;test
;run


(defn run!
  "This is the main intake point, 
   It will evaluate each node in the pipe and returns the final dataset"
  [pipe]
  (let [cpipe (clean-pipe pipe) result (ref '())] 
	(dosync
	(ref-set *result_work* (node (first cpipe)))
	(ref-set *result_work* (node (second cpipe)))
	(ref-set result @*result_work*)
	)
	(pprint @result)
	@result
	))

	(def test-pipe '(
	 {:type :csv-in,
	 :category :input,
	 :id :csv-in5293,
	 :options {:header true :separator \, :filename "test/data/P2P_2010_Contributions_short.csv"}}

	 {:type :dropzone, :id :dropzone5296}
	 
	 {:type :drop-columns,
	  :category :transformation,
	  :options {:keys [:Business_Name :Business_City :Contributor_City :Aggregate_Contribution_Amount :Contribution_Amount  :Business_Address1 :Business_Address2 :Business_Phone :Contributor_Address2 :Contributor_Address1]}
	  :id :drop-columns5295}

	 {:type :dropzone, :id :dropzone5299}
	
	 {:type :csv-out,
	  :category :output,
	  :id :csv-out5298,
	  :options {:header true, :separator ";", :filename nil}}))

