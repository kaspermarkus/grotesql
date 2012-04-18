(ns grotesql.test.input
  (:use [grotesql.input])
  (:use clojure.test))

(def test-input-csv-expected1 
	'({:Name "Kasper" :Age "31" :Country "Switzerland"}
	  {:Name "Kevin" :Age "31" :Country "Denmark"}
	  {:Name "Santa Clause" :Age "800" :Country "North Pole"}))

(def test-input-csv-expected2 
	'({:header1 "Kasper" :header2 "31" :header3 "Switzerland"}
	  {:header1 "Kevin" :header2 "31" :header3 "Denmark"}
	  {:header1 "Santa Clause" :header2 "800" :header3 "North Pole"}))

;Test input-csv function
(deftest test-input-csv 
	;read full file with headers and semi-colon separation
	(is (= (input-csv "test/grotesql/test/input.csv" \; true) test-input-csv-expected1))
	;read 2 entries from file with headers and semi-colon separation
	(is (= (input-csv "test/grotesql/test/input.csv" \; true 2) (take 2 test-input-csv-expected1)))
	;read more entries from file than exists
	(is (= (input-csv "test/grotesql/test/input.csv" \; true 200) test-input-csv-expected1))
	;read full file with no headers and comma separation
	(is (= (input-csv "test/grotesql/test/input2.csv" \, false) test-input-csv-expected2)))