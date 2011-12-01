 (ns grotesql.test.core
  (:use [grotesql.core])
  (:use [grotesql.functions])
  (:use [grotesql.data])
  (:use [grotesql.output])
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


(deftest t-multi-column

	(defnode fake-input1 emulate-input table1)
	(defnode fake-input2 emulate-input table2)

	(defnode  drop-fname drop-column [:fname])
	(defnode drop-country drop-column [:country])
	(defnode drop-city drop-column [:city])
	(defnode drop-age drop-column [:age])
	(defnode create-location str-columns :city :country :location ", ")

	(def input2-list (list drop-city drop-country create-location fake-input2))
	(def input2-pipe (create-pipe input2-list))

; create primary pipe
	(def input1-list (list drop-age 
		(partial concat-tables (input2-pipe)) drop-fname fake-input1))
	(def input1-pipe (create-pipe input1-list))

	(is (= (input1-pipe) 
			'({:lname "bbbbbbbbbb", :location "Odense, Denmark"} 
			{:lname "yyyyyyyyyyyyyy", :location "Geneva, Switzerland"} 
			{:lname "Markus", :location "Geneva"} 
			{:lname "Jørgensen", :location "Copenhagen"} 
			{:lname "Niclas", :location "North Pole"} 
			{:lname "Jesus", :location "Jerusalem"} 
			{:lname "Bagchakduak", :location "Bali"}))))



