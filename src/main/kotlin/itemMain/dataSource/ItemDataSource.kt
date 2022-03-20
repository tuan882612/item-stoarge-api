package itemMain.dataSource

import itemMain.dataLayer.Storage

interface ItemDataSource {
    fun retrieveItems(): Collection<Storage>
    
    fun retrieveItem(id: Int): Storage

    fun createItem(item: Storage): Storage

    fun updateItem(item: Storage): Storage

    fun deleteItem(id: Int)
}