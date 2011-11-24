(ns grotesql.test.functions
  (:use [grotesql.functions])
  (:use [grotesql.data])
  (:use [clojure.test]))

(deftest t-drop-column
	(is (= (drop-column [:fname :lname :age :location] table1)
	'({} {} {} {} {})))
		(is (= (drop-column [:lname :age :location] table1)
	'({:fname "Kasper"} {:fname "Kevin"} {:fname "Saint"} {:fname "Baby"} {:fname "Holalulu"})))
		(is (= (drop-column [:fname :location] table1)
	'({:lname "Markus", :age 30} {:lname "Jørgensen", :age 31} {:lname "Niclas", :age 400} {:lname "Jesus", :age 1} {:lname "Bagchakduak", :age 19})))
		(is (= (drop-column [:fname ] table1)
	'({:lname "Markus", :age 30, :location "Geneva"} {:lname "Jørgensen", :age 31, :location "Copenhagen"} {:lname "Niclas", :age 400, :location "North Pole"} {:lname "Jesus", :age 1, :location "Jerusalem"} {:lname "Bagchakduak", :age 19, :location "Bali"})))
		(is (= (drop-column [] table1)
		table1))
	
	) 
