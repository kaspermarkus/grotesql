(ns grotesql.data)

; Do we use maps (ie. no need for headers)
; or do we use some structure where we have headers separately and then lists for each row of table..
(def data 
	'( { :fname "Kasper", :lname "Markus", :age 30, :location "Geneva" }
	  { :fname "Kevin", :lname "Jørgensen", :age 31, :location "Copenhagen" }
	  { :fname "Saint", :lname "Niclas", :age 400, :location "North Pole" }
          { :fname "Baby", :lname "Jesus", :age 1, :location "Jerusalem" }
          { :fname "Holalulu", :lname "Bagchakduak", :age 19, :location "Bali" }))
