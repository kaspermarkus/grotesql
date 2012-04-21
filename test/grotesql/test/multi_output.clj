(ns grotesql.test.multi-output
  (:use [grotesql.multi-output])
  (:use clojure.test))

(def table1 '( { :a 1 :b "abe" } {:a 2 :b "orangutang"} { :a 3 :b "outsider" }))

;Test simple-join function
(deftest test-partition-rows 
    ;Regular join where 1 row gets discarded
    (is (= (partition-rows (fn [ row ] (< (get row :a) 2)) table1)
        (list '( { :a 1 :b "abe"}) '({:a 2 :b "orangutang"} { :a 3 :b "outsider" }))))

    ;first of the returned list is empty
    (is (= (partition-rows (fn [ row ] (< (get row :a) 0)) table1)
        (list '() '( { :a 1 :b "abe"} {:a 2 :b "orangutang"} { :a 3 :b "outsider" }))))

    ;first of the returned list is empty
    (is (= (partition-rows (fn [ row ] (< (get row :a) 10)) table1)
        (list '( { :a 1 :b "abe"} {:a 2 :b "orangutang"} { :a 3 :b "outsider" }) '())))

    ;input is an empty list
    (is (= (partition-rows (fn [ row ] (< (get row :a) 2)) '())
        (list '() '()))))

	