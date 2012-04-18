(ns grotesql.core
  (:use [grotesql.output]
        [grotesql.input]))

                                        ;Private

(def  ^:dynamic *result_work* (ref '()))

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

(defmulti node node-type)	

(defmethod node :csv-in [node]
  (let [options (:options node) filename (:filename options) ch (:separator options) header (:header options) ]
    (input-csv filename ch header)))

(defmethod node :csv-out [node]
  (let [options (:options node) filename (:filename options) ch (:separator options) header (:header options)]
    (output-csv filename ch header @*result_work*)))

(defmethod node :drop-columns [node]
  (let [options (:options node) k (:keys options) ]
    (drop-columns k @*result_work*  )))

(defmethod node :print-node [x]  (str x))			  	

(defmethod node :dropzone [_] nil)

(defmethod node :default [node] {:error "Not implemented" :node (str node) } )

(defmethod node :no-match [_]  {:error "Not implemented (is :type defined?)"} )

(defn clean-pipe [pipe]
  "cleans the pipe, for unused nodes (:dropzones) and :disabled? nodes returns only nodes to be evaluated"
  (filter #(not (ignore-node? %)) pipe))

                                        ;test
                                        ;run

(defn ref-work [no]
  (ref-set *result_work* (node no)))

(defn run!
  "This is the main intake point, 
   It will evaluate each node in the pipe and returns the final dataset"
  [pipe]
  (let [cpipe (clean-pipe pipe) result (ref '())] 
    ;(print pipe)
    (dosync
     (doall (map #(ref-set *result_work* (node %))  cpipe))
     ;(ref-work (first cpipe))
     ;(ref-work (second cpipe))
     ;(ref-set *result_work* (node (first cpipe)))
     ;(ref-set *result_work* (node (second cpipe)))
     (ref-set result @*result_work*))
    @result))

(def test-pipe '({:type :csv-in,
                  :category :input,
                  :id :csv-in5293,
                  :options {:header true :separator \, :filename "test/data/P2P_2010_Contributions_short.csv"}}
                 
                 {:type :dropzone, :id :dropzone5296}
                 
                 {:type :drop-columns,
                  :category :transformation,
                  :options {:keys [ :Filing_Year :Contributor_Name  :Filing_Date :Contributor_Zip :Filing_Yea :Contributor_Phone :Contribution_Date :Business_State :Business_Zip  :Business_Name :Business_City :Contributor_City :Aggregate_Contribution_Amount :Contribution_Amount  :Business_Address1 :Business_Address2 :Business_Phone :Contributor_Address2 :Contributor_Address1  :Recipient_Name  :Amendment :Contributor_State  ]}
                  :id :drop-columns5295}
                 
                 {:type :dropzone, :id :dropzone5299}
                 
                 {:type :csv-out,
                  :category :outp
                  :id :csv-out5298,
                  :options {:header true, :separator \;, :filename  "test/data/P2P_2010_Contributions_short-new.csv" }}))
