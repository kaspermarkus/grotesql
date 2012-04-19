(ns grotesql.output)
(require '[clojure.data.csv :as csv]
    '[clojure.java.io :as io])

(defn stdout
    "Prints table to standard output. Values are comma separated and rows are newline separated.

    Arguments:
    table: the data to print"
    [ table ]
    (do
        (println (apply str (interpose ", " (map name (keys (first table))))))
        (println (apply str (interpose "\n" (map (fn [row] (apply str (interpose ", " (vals row)))) table))))))

(defn output-csv
    "Writes to .csv file (comma separated values)

    filename: the file to write (string)
    separator: the separator of the csv file (char)
    header?: true if the first line of the .csv file should be a headers (boolean)"
    [ filename separator header? table ]
    (let 
        [ data-body (map (fn [row] (vec (vals row))) table) 
          out-data (if header? 
            (cons (vec (map name (keys (first table)))) data-body) 
            data-body)]
        (with-open [out-file (io/writer filename)] (csv/write-csv out-file out-data :separator separator))))
