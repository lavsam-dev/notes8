package com.geekbrains.lavsam.notes8.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NotesFirestoreRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesFirestoreRepository();
    private final static String NOTES = "notes";
    private final static String DATE = "date";
    private final static String TITLE = "title";
    private final static String TEXT = "text";
    private final static String IMAGE = "url";
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Callback<List<Note>> callback;

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        this.callback = callback;
        firebaseFirestore.collection(NOTES)
                .orderBy(DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            ArrayList<Note> result = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentID = (String) document.getId();
                                String title = (String) document.get(TITLE);
                                String text = (String) document.get(TEXT);
                                String image = (String) document.get(IMAGE);
                                Date date = ((Timestamp) document.get(DATE)).toDate();

                                result.add(new Note(document.getId(), title, text, image, date));
                            }

                            callback.onSuccess(result);

                        } else {
                            task.getException();
                        }
                    }
                });
    }

    @Override
    public void clear() {
        ArrayList<String> result = new ArrayList<>();

        firebaseFirestore.collection(NOTES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.add((String) document.getId());
                            }

//                            callback.onSuccess(result);

                        } else {
                            task.getException();
                        }
                    }
                });

        for (String docId: result) {
            firebaseFirestore.collection(NOTES)
                    .document(docId)
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                callback.onSuccess(note);
                            }
                        }
                    });
        }
    }

    @Override
    public void restore() {
        ArrayList<Note> notesArray = new ArrayList<>();

        notesArray.add(new Note("id1", "Европейский тювик (Accipiter brevipes)",
                "Европейский тювик является хищной лесной птицей и перелетным видом, с приближением зимы эти пернатые собираются в крупные стаи и отправляются в теплые края. Размеры тювика европейского средние, длина тела достигает 40 см, средний размах крыльев около 70 см, вес до 200 г. Оперение верха тела самца светло-серое, белоснежное горло украшено темными узорами, грудка рыжая. Самка окрашена в серо-коричневый цвет на спинке, белое горло украшает темная продольная полоса, низ тела светлый с каштановыми пестринами. Европейский тювик относится к активны дневным хищникам и ведет охоту на земноводных, грызунов, птиц мелких размеров и на крупных насекомых. Добычу ловит с воздуха.",
                "https://www.niasam.ru/allimages/109815.jpg", new Date()));
        notesArray.add(new Note("id2", "Белоголовый сип (Gyps fulvus)",
                "На открытых просторах Самарской области встречается такой крупный хищник-падальщик, как белоголовый сип. Вид является редким, входит в Красную книгу, а его места обитания и популяция подвергаются постепенному сокращению. Среди своих сородичей белоголовый сип выделяется достаточно крупными размерами, а также у него широкие крылья и хвост. В длину представители вида около 1 м, размах крыльев достигает 2,5 м. Небольшого размера голову птицы покрывает белый пух, на шее длинные перья складываются в воротник. Верх тела бурого цвета, низ кремовый с рыжиной. Крылья темные. Рацион белоголового сипа состоит исключительно из падали, трупов млекопитающих, в поисках которых птица может дни и даже недели проводить совсем без пищи.",
                "https://www.niasam.ru/allimages/109816.jpg", new Date()));
        notesArray.add(new Note("id3", "Орёл-карлик (Aquila pennata)",
                "Орлы-карлики – пернатые хищники небольших размеров, гнездятся в лесах. В длину достигают 50 см, размах крыльев превышает 1 м, вес около 1 кг. Самки всегда по размерам немного больше, чем самцы. А вот в окрасе представители обоих полов одинаковы. Для орлов-карликов различают два подвида: светлый и темный. У светлых птиц верх тела бурый, а низ кремовый. Темные особи имеют полностью коричнево-бурое оперение. Характерными чертами во внешности этого хищника являются крупная голова, сильный загнутый книзу клюв и сильные лапы, оперенные вплоть до пальцев. Эти особенности также дают орлу-карлику возможность охотиться на добычу разных размеров, в том числе и на способную к быстрым передвижениям. Это и птицы, и млекопитающие, и рептилии.",
                "https://www.niasam.ru/allimages/109817.jpg", new Date()));
        notesArray.add(new Note("id4", "Краснозобый конёк (Anthus cervinus)",
                "Одна из певчих птиц Самарской области – краснозобый конек – достигает длину 15 см. Спинка ее окрашена в бурый цвет с темными полосками. Коричневое горлышко имеет выраженный рыжий оттенок, а грудка и бока украшены красноватыми рисунками с черными пятнышками. Красноватыми эти части тела остаются, в том числе, и зимой, когда все оперение в целом немного бледнеет, из-за чего вид и получил свое название. Животик желтовато-белого цвета. Молодняк отличается горлом белого цвета. От родственных видов краснозобого конька отличают белые надбровья, белые окологлазные кольца и белесые полоски вдоль спины. Песни свои эти птицы исполняют обычно на лету, переливчатые трели «тсиви-тсиви-тсиви-висс-висс-висс-твисс-висс-висс-цирррр» заканчиваются трещащим звуком.",
                "https://www.niasam.ru/allimages/109818.jpg", new Date()));
        notesArray.add(new Note("id5", "Чернолобый сорокопут (Lanius minor)",
                "Перелетные певчие птицы чернолобые сорокопуты встречаются в ландшафтах, где на открытых просторах есть отдельные деревья или заросли кустарников, на лесных опушках и полянах, окраинах полей, в лесополосах и других подобных территориях. Размеры представителей этого вида средние, голова имеет округлую форму, хвост короткий, клюв небольшой мощный. Длина тела чернолобого сорокопута около 25 см, размах крыльев достигает 40 см, вес до 60 г. Окраска оперения черная с белым, только животик имеет легкий розовый оттенок. Верх темный, черные крылья и хвост украшены белыми узорами. На серой голове ярко выделяется широкая угольно-черная «маска». Вокализация у чернолобых сорокопутов заливистая, песни энергичные и громкие, состоят из свистящих трелей.",
                "https://www.niasam.ru/allimages/109819.jpg", new Date()));
        notesArray.add(new Note("id6", "Обыкновенный скворец (Sturnus vulgaris)",
                "Обыкновенный скворец, который внешностью напоминает черного дрозда, является широко распространенной в России певчей птицей, которая ведет как оседлый, так и кочующий и даже мигрирующий образы жизни. В длину он достигает 22 с, размах крыльев около 40 см, масса до 80 г. Отличительными чертами являются плотное телосложение, короткая шея, длинный изогнутый черный клюв, который желтеет во время брачного периода. Короткими крылышками скворец во время пения совершает взмахивающие движения. Спина, грудь и шея птицы черная, блестящая, с фиолетовым, синим, изумрудным или каштановым оттенком. Хвост короткий. Лапки бурые. В зимний период оперение становится бурым в белую крапинку. Вокализация очень разнообразная, включает множество звуков, в том числе и подражающие другим видам птиц.",
                "https://www.niasam.ru/allimages/109820.jpg", new Date()));
        notesArray.add(new Note("id7", "Крапивник (Troglodytes troglodytes)",
                "В хвойных и смешанных темных сырых лесах обитает птица под названием крапивник. Густые заросли и близость водоема ей для жизни абсолютно необходимы, и на открытых просторах она не встречается никогда. Внешность у крапивника довольно интересная. Птичка длиной около 10 см, с размах крыльев до 20 см и весом примерно 10 г выглядит, будто перьевой шарик с хвостом, а все потому что у нее короткая шея и крупная голова. Окраска верха тела рыжевато-бурая, животик буро-серого цвета. Все тело испещрено темными поперечными полосками, особенно яркими в области крыльев и хвоста. Крапивник чрезвычайно подвижен, громко и красиво поет. Песня у него быстрая, состоит из трещащих и звенящих звуков и немного похожа на пение канареек.",
                "https://www.niasam.ru/allimages/109821.jpg", new Date()));
        notesArray.add(new Note("id8", "Пепельная чечётка (Carduelis hornemanni)",
                "В длину пепельная чечетка около 15 см, внешне очень напоминает своих ближайших родственниц обыкновенных чечеток, от которых отличается более светлым оперением, а также белоснежным, а не полосатым надхвостьем. Спинка у птицы серого цвета, голова светлая, животик также светлый, полосатый. У самцов выражен розоватый отлив на груди, у самочек его нет. Темя у представителей обоих полов яркое алое. Вокализация состоит из ясных зычных трелей. Пепельная чечетка принадлежит к группе типичных лесных птиц. Обитает она в основном в хвойных лесах, в тундре, среди зарослей густых кустарников. Питается как растительной, так и животной пищей. А с обыкновенными чечетками очень часто образует смешанные пары.",
                "https://www.niasam.ru/allimages/109822.jpg", new Date()));
        notesArray.add(new Note("id9", "Белокрылый клёст (Loxia leucoptera)",
                "Еще одними жителями лесного биотопа Самарской области являются белокрылые клесты, которые в то же время известны и благодаря своим великолепным вокальным данным. Поют обычно самцы этого вида в период размножения. В это время они часто протяжно высоко свистят, сплетая звуки «софи-и-и-и-и-и-и» в единую красивую мелодию. Длина тела представителей вида около 16 см. Клюв сильный, помогает птице раскалывать шишки хвойных пород деревьев и добывать семена, которыми она преимущественно питается. Оперение у самцов и самок этого вида отличается кардинально. Самочки окрашены в золотистый цвет, самцы – малиново-красные или оранжево-красные. Крылья черные с двумя белоснежными полосами.",
                "https://www.niasam.ru/allimages/109823.jpg", new Date()));
        notesArray.add(new Note("id10", "Глухая кукушка (Cuculus optatus)",
                "Глухая кукушка немного уступает в размерах кукушке обыкновенной, но в остальном различить этих двух птиц довольно сложно, ведь они похожи внешне и по образу жизни. Оба вида являются оседлыми, но глухая кукушка все же более скрытная и встречается в основном на территории густых хвойных лесов. Кроме того, голос этих птиц имеет более глухое звучание. Это не классическое ясное «ку-ку», а низкий звук «ууу-ту-ту ууу-ту ууу-ту». Размеры тела у глухой кукушки средние. В длину она достигает 45 см, размах крыльев около 60 см, вес до 100 г. Спинка самца сизого цвета, хвост темный, грудка светло-серая. Другие части тела белые с темными полосками. Клюв загибается вверх. Короткие лапки окрашены в желтый цвет.",
                "https://www.niasam.ru/allimages/109824.jpg", new Date()));
        notesArray.add(new Note("id11", "Белая трясогузка (Motacilla alba)",
                "В длину белые трясогузки достигают 20 см, вес около 25 г. Характерной чертой во внешности птицы является длинный хвост, которым она постоянно совершает качающиеся движения. Оперение верха тела серого цвета, снизу – белого. Голова белоснежная, украшена черной шапочкой. Горло также черное. Белая трясогузка ведет оседлый образ жизни на территории парков и садов Самарской области, не боится людей и активно заселяет антропогенный ландшафт. Свое гнездо эта птицы вполне может построить под крышей здания или среди бревен, порой даже занимает гнезда, созданные людьми для других видов птиц. Питаются белые трясогузки животной пищей, насекомыми, на растительный корм переходят очень редко.",
                "https://www.niasam.ru/allimages/109825.jpg", new Date()));
        notesArray.add(new Note("id12", "Обыкновенная чечевица (Carpodacus erythrinus)",
                "В лесах Самарской области рядом с открытыми просторами живет оседло обыкновенная чечевица – птичка, по размерам примерно, как домовой воробей. Встретить ее можно в зарослях кустарника на лесных опушках или рядом с водоемом. А так как большую часть времени эта кроха усердно скрывается от посторонних глаз, то ее присутствие выдает обычно короткая переливчатая свистящая трель, состоящая из набора характерных звуков, складывающихся в слова «виитю-виидел». Верх тела самцы обыкновенной чечевицы красновато-бурый, голова и грудка яркие алые, животик бело-розовый. Оперение у самочек скромное, буровато-серого цвета со светлым животиком.",
                "https://www.niasam.ru/allimages/109826.jpg", new Date()));

        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        for (Note note: notesArray) {
            data.put(TITLE, note.getTitle());
            data.put(TEXT, note.getText());
            data.put(IMAGE, note.getUrl());
            data.put(DATE, date);
            firebaseFirestore.collection(NOTES)
                    .add(data)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Note noteAdd = new Note(task.getResult().getId(),
                                        note.getTitle(), note.getText(), note.getUrl(), date);

                                callback.onSuccess((List<Note>) noteAdd);
                            }
                        }
                    });

        }
    }

    @Override
    public void add(String title, String text, String imageUrl, Callback<Note> callback) {
        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        data.put(TITLE, title);
        data.put(TEXT, text);
        data.put(IMAGE, imageUrl);
        data.put(DATE, date);

        firebaseFirestore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Note note = new Note(task.getResult().getId(), title, text, imageUrl, date);

                            callback.onSuccess(note);
                        }
                    }
                });
    }

    @Override
    public void remove(Note note, Callback<Object> callback) {
        firebaseFirestore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(note);
                        }
                    }
                });

    }

    @Override
    public Note update(@NonNull Note note, @Nullable String title, @Nullable String text, @Nullable Date date) {
        return null;
    }
}
