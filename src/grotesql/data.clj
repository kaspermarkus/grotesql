(ns grotesql.data)

; Do we use maps (ie. no need for headers)
; or do we use some structure where we have headers separately and then lists for each row of table..
(def table1 
	'({ :fname "Kasper", :lname "Markus", :age 30, :location "Geneva" }
	  { :fname "Kevin", :lname "Jørgensen", :age 31, :location "Copenhagen" }))