(ns grotesql.test.multi-input
  (:use [grotesql.multi-input])
  (:use clojure.test))


(def table1 '( { :a 1 :b "abe" } {:a 2 :b "orangutang"} { :a 3 :b "outsider" }))
(def table2 '( { :a 1 :x "motorcykel" } { :a 1 :x "tomat" } { :a 2 :x "skitur" } ))
(def table3 '( { :match 1 :b "football" } ))
(def table4 '())


;Test simple-join function
(deftest test-simple-join 
	;Regular join where 1 row gets discarded
	(is (= (simple-join :a :a table1 table2)
		'({ :x "motorcykel" :a 1 :b "abe"} {:x "tomat" :a 1 :b "abe"} {:x "skitur" :a 2 :b "orangutang"})))
	;Join with empty list
	(is (= (simple-join :a :b table1 table4)
		'()))
	;No matches:
	(is (= (simple-join :match :b table3 table1)
		'()))
	;Join with overwriting a value and join is on different column names
	(is (= (simple-join :match :a table3 table1))
		'({ :a 1 :match 1 :b "football" } { :a 1 :match 1 :b "football" } )))