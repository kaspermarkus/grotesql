(ns grotesql.multi-input)

(defn simple-join 
    "Takes two tables as input and does a simple join. The join is done on the columns given as parameter.
    If a row doesn't match anything in the other table it will be dropped, if it matches the result will
    be a row with the columns from both the tables. If two columns have the same keyword(name), the first
    will be overwritten with the value from the latter.

    join1: The column to join in table1
    join2: The column to join in table2
    table1: The first table to join on
    table2: The second table to join on"
    ([ join1 join2 table1 table2 ]
        (flatten 
            (map 
                (fn [ t1 ]
                    (filter #(not (nil? %)) 
                        (map 
                            (fn [ t2 ] (if (= (get t1 join1) (get t2 join2)) (merge t1 t2)))
                            table2)))                   
                table1)))) 
