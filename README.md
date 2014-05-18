Known Problems
--------------
* shift clicking bypasses isValidForSlot allowing everything to be placed in the slot (should be just written letters)
* boxes sometimes unregister (the list is lost, dont know how to fix that, I should research worldsavedata more closely)
* the tick handler and packet management are a mess

To Do
-----
* mailbox
    * sizing + bounding box
        * check BlockBed for placing fake 2nd block to trigger gui/bouding box showing etc
    * textures:
        * mapping
        * making
    * make slot only accept letters
    * add little "got mail" indicator/flag uioverlay
* letter item
