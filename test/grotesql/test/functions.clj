(ns grotesql.test.functions
  (:use [grotesql.functions])
  (:use clojure.test))

(def testdata
	'({:header1 "a-string" :header2 111 :header3 :a}
	  {:header1 "b-string" :header2 222 :header3 :b}
	  {:header1 "c-string" :header2 333 :header3 :c}))

;Test drop-columns function
(deftest drop-columns-test
  (is (= (drop-columns [:header3 :header2] testdata) '({:header1 "a-string"} {:header1 "b-string" } {:header1 "c-string"})))
  (is (= (drop-columns [:header1 :header2] testdata) '({:header3 :a} {:header3 :b } {:header3 :c})))
  (is (= (drop-columns [:header1 :header3] testdata) '({:header2 111} {:header2 222} {:header2 333}))))

(deftest str-columns-test
  (is (= (drop-columns [:header1 :header2 :header3] (str-columns :header3 :header2 :headernew "--" testdata)) '({:headernew ":a--111"} {:headernew ":b--222" } {:headernew":c--333"}))))