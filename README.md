# LeroyLogistics
Короче пишу, чтобы ты не забыл. На данный момент там много лишних файлов, под которые я пытался нормально вписать БД. К ним относятся WorkerGridViewActivity.java,
WorkerDataAdapter, activity_workers_db.xml, list_gridview_item.xml., вся директория goodsTest, activity_goods.xml
# Что реализовано
База данных с сотрудниками и товарами. При логине с ником "0000" заходим как админ и получаем список работников в который на данный момент можно только добавлять новых. После того как мы добавляем сотрудников, по коду любого сотрудника можно уже зайти как сотрудник и получить таблицу товаров. В таблице товаров на данный момент работает только добавление нового товара.  (проверки на ввод не реализованы да и они не очень нужны в рамках второй практической я думаю)
# Что ты можешь сделать
1. Реализовать удаление товара/сотрудника (удобнее будет через контекстное меню)
2. Реализовать редактирование данных товара/сотрудника
3. Разграничить таблицу с товарами в зависимости от уровня доступа сотрудника (Полный - изменение всего, Частичный - изменение только количества, Без доступа - заперт на изменение)
4. Написать небольшую функцию, где если количество товара < минимального остатка => подстветить красным строку ( мне лень )
5. Нам в последствии нужно будет перейти на RecyclerView возможно потому что ListView много памяти жрет но надеюсь они хер поймут что в них какая-то разница
