;;;;;;;;;;;;; load stuff
(use 'grotesql.data)
(use 'grotesql.input)
(use 'grotesql.output)
(use 'grotesql.functions)
(use 'grotesql.core)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; EXAMPLE 1:
; Read 5000 entries from a large file
; Merge two fields (Businesss_Address1 and Business_City) into a new field (Address_info)
; Drop a whole bunch of fields
; Output to a new semi-colon separated file
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;; Define nodes
; Merge Bussiness_Address1 and Business_City
(defnode merge-address str-columns :Business_Address1 :Business_City :Address_info ", ")

; Drop most of the fields:
(defnode drop-fields drop-column [ :Contributor_Name :Business_Address2 :Contributor_Address1 :Business_City :Contributor_City :Contributor_Address2 :Recipient_Name :Amendment :Contributor_State :Filing_Year :Vendor_ID  :Business_State :Contributor_Phone :Business_Zip :Contribution_Date  :Contributor_Zip ] )

; Input node - read 5000 lines
(defnode csv-reader input-csv "test/data/P2P_2010_Contributions.csv", \, true, 5000)

; Output node - separate with ; and print headers
(defnode csv-writer output-csv "modified-file.csv" \; true)

; create pipe
(def example1-list (list csv-writer drop-fields merge-address csv-reader))
(def example1-pipe (create-pipe example1-list))

;run it:
(example1-pipe)

