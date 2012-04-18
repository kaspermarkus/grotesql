(ns grotesql.test.output
  (:use [grotesql.output])
  (:use clojure.test))

(def test-output-csv-data1 
	'({:Name "Kasper" :Age "31" :Country "Switzerland"}
	  {:Name "Kevin" :Age "31" :Country "Denmark"}
	  {:Name "Santa Clause" :Age "800" :Country "North Pole"}))

(def test-output-csv-expected1
	"Name;Age;Country\nKasper;31;Switzerland\nKevin;31;Denmark\nSanta Clause;800;North Pole\n")

(def test-output-csv-expected2
	"Kasper,31,Switzerland\nKevin,31,Denmark\nSanta Clause,800,North Pole\n")

(defn write-n-slurp 
	"function calls the list given as first argument, slurps the filename (second entry in list) and returns content"
	[ filename separator header? data ]
	(do (output-csv filename separator header? data)
		(slurp filename)))

;Test output-csv function
(deftest test-output-csv 
	;write file with headers and semi-colon separation
	(is (= 
		(write-n-slurp "test/grotesql/test/output.csv" \; true test-output-csv-data1) 
		test-output-csv-expected1))
	;write file with NO headers and comma separation
	(is (= 
		(write-n-slurp "test/grotesql/test/output.csv" \, false test-output-csv-data1) 
		test-output-csv-expected2)))

(def test-stdout-expected 
	"Name, Age, Country\nKasper, 31, Switzerland\nKevin, 31, Denmark\nSanta Clause, 800, North Pole\n")

;Test the stdout function
(deftest test-stdout
	(is (= (with-out-str (stdout test-output-csv-data1)) test-stdout-expected)))