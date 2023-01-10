package com.example.elearn

class QueData {
    lateinit var list: MutableList<Model3>

    init {
        list =ArrayList()

        list.add(Model3("1. Which one of the following river flows between Vindhyan and Satpura ranges?","Narmada","Mahanadi","Son","Netravati","Narmada",0))
        list.add(Model3("2. The Central Rice Research Station is situated in?","Chennai","Cuttack","Bangalore","Quilon","Cuttack",0))
        list.add(Model3("3. Who among the following wrote Sanskrit grammar?","Kalidasa","Charak","Panini","Aryabhatt","Panini",0))
        list.add(Model3("4. Which among the following headstreams meets the Ganges in last?","Alaknanda","Pindar","Mandakini","Bhagirathi","Bhagirathi",0))
        list.add(Model3("5. The metal whose salts are sensitive to light is?","Zinc","Silver","Copper","Aluminum","Silver",0))
        list.add(Model3("6. Patanjali is well known for the compilation of –","Yoga Sutra","Panchatantra","Brahma Sutra","Ayurveda","Yoga Sutra",0))
        list.add(Model3("7. River Luni originates near Pushkar and drains into which one of the following?","Rann of Kachchh","Arabian Sea","Gulf of Cambay","Lake Sambhar","Rann of Kachchh",0))
        list.add(Model3("8. Which one of the following rivers originates in Brahmagiri range of Western Ghats?","Pennar","Cauvery","Krishna","Tapti","Cauvery",0))
        list.add(Model3("9. The country that has the highest in Barley Production?","China","India","Russia","France","Russia",0))
        list.add(Model3("10. Tsunamis are not caused by","Hurricanes","Earthquakes","Undersea landslides","Volcanic eruptions","Hurricanes",0))

    }




    fun getlist() : MutableList<Model3>
    {
        return  list
    }
}