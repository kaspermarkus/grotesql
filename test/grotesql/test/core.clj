 (ns grotesql.test.core
  (:use [grotesql.core])
  (:use [grotesql.functions])
  (:use [grotesql.data])
  (:use [clojure.test]))

(deftest t-drop-column
	(defnode node1 drop-column [:fname :lname :age :location])
	(defnode node2 drop-column [:fname])
	
	(is (= (node1 table1))
	'({} {} {} {} {}))
	(is (= (node2 table1)
	'({:lname "Markus", :age 30, :location "Geneva"} 
	{:lname "Jørgensen", :age 31, :location "Copenhagen"} 
	{:lname "Niclas", :age 400, :location "North Pole"} 
	{:lname "Jesus", :age 1, :location "Jerusalem"} 
	{:lname "Bagchakduak", :age 19, :location "Bali"}))))
