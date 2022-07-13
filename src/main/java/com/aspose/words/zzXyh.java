//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.aspose.words;

import com.aspose.words.internal.zzZj0;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

final class zzXyh {
    private String zzWgt;
    private String zzYT7;
    private int zzWJa = 4096;
    private long zzVVD = 0L;
    private long zzZ4 = 0L;
    private String zz1h;
    private String zzYxm;
    private String zzZAL;
    private String zzq0 = zzZj0.zzYtN().zzZYr(new byte[]{115, 65, 111, 112, 101, 115});
    private final byte[] zzY0F = new byte[]{73, 110, 118, 97, 108, 105, 100, 32, 108, 105, 99, 101, 110, 115, 101, 32, 115, 105, 103, 110, 97, 116, 117, 114, 101, 46, 32, 80, 108, 101, 97, 115, 101, 32, 109, 97, 107, 101, 32, 115, 117, 114, 101, 32, 116, 104, 101, 32, 108, 105, 99, 101, 110, 115, 101, 32, 102, 105, 108, 101, 32, 119, 97, 115, 32, 110, 111, 116, 32, 109, 111, 100, 105, 102, 105, 101, 100, 46};
    private static zzXyh zzWww;
    private static final byte[] zzYat;

    public zzXyh() {
        try {
            this.zzZ4h();
        } catch (Exception var1) {
            throw new IllegalStateException(zzZj0.zzYtN().zzXIn(this.zzY0F));
        }

        if (zzPf.zzZ1l()) {
            this.zz1h = zzZj0.zzYtN().zzZYr(new byte[]{57, 54, 54, 102, 98, 56, 54, 56, 57, 55, 97, 55, 98, 52, 100, 53, 101, 98, 99, 57});
            this.zzYxm = zzZj0.zzYtN().zzZYr(new byte[]{100, 54, 50, 98, 50, 52, 53, 102, 56, 54, 49, 52, 101, 52, 56, 97, 57, 57, 97, 97});
            this.zzZAL = zzZj0.zzYtN().zzZYr(new byte[]{116, 104, 112, 116, 58, 115, 47, 47, 117, 112, 99, 114, 97, 104, 101, 115, 97, 45, 105, 112, 113, 45, 46, 97, 121, 100, 97, 110, 105, 98, 46, 99, 111, 99, 47, 109, 49, 118, 50, 46});
        } else {
            this.zz1h = zzZj0.zzYtN().zzZYr(new byte[]{101, 98, 55, 100, 102, 98, 100, 97, 51, 51, 49, 48, 97, 52, 48, 97, 97, 110, 54, 100});
            this.zzYxm = zzZj0.zzYtN().zzZYr(new byte[]{49, 55, 97, 98, 98, 51, 100, 51, 97, 102, 97, 97, 99, 104, 49, 122, 49, 57, 98, 100});
            this.zzZAL = zzZj0.zzYtN().zzZYr(new byte[]{116, 104, 112, 116, 58, 115, 47, 47, 117, 112, 99, 114, 97, 104, 101, 115, 97, 45, 105, 112, 100, 46, 110, 121, 98, 97, 99, 105, 99, 46, 109, 111, 118, 47, 46, 49, 50});
        }

        boolean var10000 = zzPf.zzXcW;
    }

    final boolean zzYYp(String var1, String var2) {
        boolean var3 = false;
        boolean var4 = false;

        boolean var6;
        try {
            if (var1 != null && var2 != null && var1.length() != 0 && var2.length() != 0) {
                List var5;
                if ((var5 = this.zzZYr(var1, var2)) != null) {
                    var5.size();
                }

                if (var6 = zzOk(var5)) {
                    this.zzWgt = var1;
                    this.zzYT7 = var2;
                    this.zzWJa = 256;
                    zzWww = this;
                }

                var3 = var6;
                var4 = true;
            }
        } catch (zzXCv var19) {
            if (var19.zz7G() == 500) {
                var6 = false;
                zzWQQ.zzFo().zzXGK.lock();

                try {
                    if (zzWQQ.zzFo().zzXEL == -1L) {
                        zzWQQ.zzFo().zzXEL = System.currentTimeMillis();
                    }

                    if (System.currentTimeMillis() - zzWQQ.zzFo().zzXEL > 86400000L) {
                        var6 = true;
                    }
                } finally {
                    zzWQQ.zzFo().zzXGK.unlock();
                }

                if (!var6) {
                    this.zzWgt = var1;
                    this.zzYT7 = var2;
                    this.zzWJa = 256;
                    zzWww = this;
                    var3 = true;
                }
            }
        } catch (Exception var20) {
        }

        if (var4) {
            zzWQQ.zzFo().zzXGK.lock();

            try {
                zzWQQ.zzFo().zzXEL = -1L;
            } finally {
                zzWQQ.zzFo().zzXGK.unlock();
            }
        }

        return var3;
    }

    private static boolean zzOk(List<zzW3g> var0) {
        boolean var1 = false;
        String var2;
        if (var0 != null && var0.size() > 0 && (var2 = ((zzW3g)var0.get(0)).zzXDB()) != null && ((var2 = var2.toLowerCase()).contains(zzZj0.zzYtN().zzZYr(new byte[]{99, 97, 105, 116, 101, 118})) || var2.contains(zzZj0.zzYtN().zzZYr(new byte[]{114, 116, 97, 105, 105, 108, 103, 110})) || var2.contains(zzZj0.zzYtN().zzZYr(new byte[]{105, 98, 108, 108, 111, 110, 112, 116, 105, 97, 111, 100, 116, 110, 109, 105, 114, 101, 116, 101, 121, 114, 110, 105, 103})))) {
            var1 = true;
        }

        return var1;
    }

    private List<zzW3g> zzZYr(String var1, String var2) {
        zzW26 var4 = zzW26.zzWAe(new zzK9(this.zz1h, this.zzYxm), this.zzZAL, false);
        String var5 = zzZj0.zzYtN().zzZYr(new byte[]{115, 47, 98, 117, 99, 115, 105, 114, 116, 112, 111, 105, 115, 110, 115, 47, 116, 105, 45, 101, 115, 123, 116, 105, 83, 101, 98, 117, 111, 100, 97, 109, 110, 105, 63, 125, 116, 115, 116, 97, 115, 117, 123, 61, 116, 115, 116, 97, 115, 117, 38, 125, 97, 112, 101, 103, 117, 78, 98, 109, 114, 101, 123, 61, 97, 112, 101, 103, 117, 78, 98, 109, 114, 101, 38, 125, 97, 112, 101, 103, 105, 83, 101, 122, 123, 61, 97, 112, 101, 103, 105, 83, 101, 122, 38, 125, 117, 112, 108, 98, 99, 105, 112, 65, 75, 105, 121, 101, 123, 61, 117, 112, 108, 98, 99, 105, 112, 65, 75, 105, 121, 101, 38, 125, 114, 112, 118, 105, 116, 97, 65, 101, 105, 112, 101, 75, 61, 121, 112, 123, 105, 114, 97, 118, 101, 116, 112, 65, 75, 105, 121, 101, 125}).replace(zzZj0.zzYtN().zzZYr(new byte[]{102, 123, 114, 111, 97, 109, 125, 116}), zzZj0.zzYtN().zzZYr(new byte[]{109, 120, 108})).replaceAll(zzZj0.zzYtN().zzZYr(new byte[]{42, 92}), "");
        String var6 = zzZj0.zzYtN().zzZYr(new byte[]{69, 71, 84});
        HashMap var7 = new HashMap();
        new HashMap();
        String var8 = this.zzq0;
        String var9;
        if ((var9 = var1.toLowerCase()).startsWith(zzZj0.zzYtN().zzZYr(new byte[]{115, 97, 111, 112, 101, 115, 111, 102, 99, 114, 111, 108, 100, 117}))) {
            var8 = zzZj0.zzYtN().zzZYr(new byte[]{115, 97, 111, 112, 101, 115, 111, 102, 99, 114, 111, 108, 100, 117});
        } else if (var9.startsWith(zzZj0.zzYtN().zzZYr(new byte[]{114, 103, 117, 111, 100, 112, 99, 111, 102, 115, 114, 111, 108, 99, 117, 111, 50, 100}))) {
            var8 = zzZj0.zzYtN().zzZYr(new byte[]{114, 103, 117, 111, 100, 112, 99, 111, 102, 115, 114, 111, 108, 99, 117, 111, 50, 100});
        } else if (var9.startsWith(zzZj0.zzYtN().zzZYr(new byte[]{111, 99, 104, 110, 108, 111, 97, 100, 101, 116, 111, 102, 99, 114, 111, 108, 100, 117}))) {
            var8 = zzZj0.zzYtN().zzZYr(new byte[]{111, 99, 104, 110, 108, 111, 97, 100, 101, 116, 111, 102, 99, 114, 111, 108, 100, 117});
        } else if (var9.startsWith(zzZj0.zzYtN().zzZYr(new byte[]{111, 99, 104, 110, 108, 111, 97, 100, 101, 116}))) {
            var8 = zzZj0.zzYtN().zzZYr(new byte[]{111, 99, 104, 110, 108, 111, 97, 100, 101, 116});
        }

        var5 = var5.replace(zzZj0.zzYtN().zzZYr(new byte[]{115, 123, 116, 105, 83, 101, 98, 117, 111, 100, 97, 109, 110, 105, 125}), zzW26.zzW15(var8)).replace(zzZj0.zzYtN().zzZYr(new byte[]{112, 123, 98, 117, 105, 108, 65, 99, 105, 112, 101, 75, 125, 121}), zzW26.zzW15(var1)).replace(zzZj0.zzYtN().zzZYr(new byte[]{112, 123, 105, 114, 97, 118, 101, 116, 112, 65, 75, 105, 121, 101, 125}), zzW26.zzW15(var2)).replaceAll(zzZj0.zzYtN().zzZYr(new byte[]{123, 92, 119, 92, 92, 42, 125}), "");
        String var10;
        if ((var10 = var4.zzWAe(var5, var6, var7, (String)null)).length() == 0) {
            return null;
        } else {
            try {
                StringReader var11 = new StringReader(var10);
                InputSource var12 = new InputSource(var11);
                DocumentBuilderFactory var13;
                (var13 = DocumentBuilderFactory.newInstance()).setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                Element var16 = var13.newDocumentBuilder().parse(var12).getDocumentElement();
                ArrayList var17 = new ArrayList();
                if (var16 != null) {
                    NodeList var18 = var16.getChildNodes();

                    for(int var19 = 0; var19 < var18.getLength(); ++var19) {
                        Node var20 = var18.item(var19);
                        var17.add(this.zzYp8(var20));
                    }
                }

                return var17;
            } catch (Exception var21) {
                throw new zzXCv(1006, var10 + zzZj0.zzYtN().zzZYr(new byte[]{69, 32, 114, 114, 114, 111, 105, 32, 32, 110, 111, 99, 118, 110, 114, 101, 105, 116, 103, 110, 114, 32, 115, 101, 111, 112, 115, 110, 32, 101, 115, 106, 110, 111, 118, 32, 108, 97, 101, 117, 116, 32, 32, 111, 97, 106, 97, 118, 111, 32, 106, 98, 99, 101, 32, 116, 32, 58}) + var21.getMessage());
            }
        }
    }

    static int zzY07() {
        return 1;
    }

    final BigDecimal zzXh6() {
        if (this.zzWgt != null && this.zzYT7 != null) {
            BigDecimal var1 = BigDecimal.ZERO;

            try {
                List var2;
                if ((var2 = this.zzZYr(this.zzWgt, this.zzYT7)) != null && var2.size() > 0) {
                    zzW3g var3 = (zzW3g)var2.get(0);
                    List var4;
                    if ((var4 = this.zzZGo(var3.zzw4())) != null && var4.size() > 0) {
                        Iterator var5 = var4.iterator();

                        while(var5.hasNext()) {
                            zzXjx var6 = (zzXjx)var5.next();
                            if (zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 99, 111, 115, 101, 101, 115, 32, 100, 117, 81, 110, 97, 105, 116, 121, 116}).equals(var6.getName())) {
                                var1 = var6.zzZIq();
                                break;
                            }
                        }
                    }

                    return var1;
                } else {
                    throw new IllegalStateException(zzZj0.zzYtN().zzZYr(new byte[]{104, 84, 114, 101, 32, 101, 115, 105, 110, 32, 32, 111, 117, 115, 115, 98, 114, 99, 112, 105, 105, 116, 110, 111, 46}));
                }
            } catch (Exception var7) {
                throw new IllegalStateException(var7.getMessage());
            }
        } else {
            throw new IllegalStateException(zzZj0.zzYtN().zzZYr(new byte[]{111, 89, 32, 117, 104, 115, 117, 111, 100, 108, 115, 32, 116, 101, 109, 32, 116, 101, 114, 101, 100, 101, 107, 32, 121, 101, 102, 32, 114, 105, 116, 115, 121, 108, 46}));
        }
    }

    final BigDecimal zzZGe() {
        if (this.zzWgt != null && this.zzYT7 != null) {
            BigDecimal var1 = BigDecimal.ZERO;

            try {
                List var2;
                if ((var2 = this.zzZYr(this.zzWgt, this.zzYT7)) != null && var2.size() > 0) {
                    zzW3g var3 = (zzW3g)var2.get(0);
                    List var4;
                    if ((var4 = this.zzZGo(var3.zzw4())) != null && var4.size() > 0) {
                        Iterator var5 = var4.iterator();

                        while(var5.hasNext()) {
                            zzXjx var6 = (zzXjx)var5.next();
                            if (zzZj0.zzYtN().zzZYr(new byte[]{114, 67, 100, 101, 116, 105, 115}).equals(var6.getName())) {
                                var1 = var6.zzZIq();
                                break;
                            }
                        }
                    }

                    return var1;
                } else {
                    throw new IllegalStateException(zzZj0.zzYtN().zzZYr(new byte[]{104, 84, 114, 101, 32, 101, 115, 105, 110, 32, 32, 111, 117, 115, 115, 98, 114, 99, 112, 105, 105, 116, 110, 111, 46}));
                }
            } catch (Exception var7) {
                throw new IllegalStateException(var7.getMessage());
            }
        } else {
            throw new IllegalStateException(zzZj0.zzYtN().zzZYr(new byte[]{111, 89, 32, 117, 104, 115, 117, 111, 100, 108, 115, 32, 116, 101, 109, 32, 116, 101, 114, 101, 100, 101, 107, 32, 121, 101, 102, 32, 114, 105, 116, 115, 121, 108, 46}));
        }
    }

    private List<zzXjx> zzZGo(Long var1) throws zzXCv {
        zzW26 var3 = zzW26.zzWAe(new zzK9(this.zz1h, this.zzYxm), this.zzZAL, false);
        String var4 = zzZj0.zzYtN().zzZYr(new byte[]{115, 47, 98, 117, 99, 115, 105, 114, 116, 112, 111, 105, 115, 110, 123, 47, 100, 105, 47, 125, 116, 105, 109, 101, 115}).replace(zzZj0.zzYtN().zzZYr(new byte[]{102, 123, 114, 111, 97, 109, 125, 116}), zzZj0.zzYtN().zzZYr(new byte[]{109, 120, 108})).replaceAll(zzZj0.zzYtN().zzZYr(new byte[]{42, 92}), "");
        String var5 = zzZj0.zzYtN().zzZYr(new byte[]{69, 71, 84});
        HashMap var6 = new HashMap();
        new HashMap();
        if (var1 != null) {
            var4 = var4.replace("{id}", zzW26.zzYBY(var1));
        }

        var4 = var4.replaceAll("\\{\\w*\\}", "");
        String var7;
        if ((var7 = var3.zzWAe(var4, var5, var6, (String)null)).length() == 0) {
            return null;
        } else {
            try {
                StringReader var8 = new StringReader(var7);
                InputSource var9 = new InputSource(var8);
                DocumentBuilderFactory var10;
                (var10 = DocumentBuilderFactory.newInstance()).setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                Element var13;
                Object var14;
                if ((var13 = var10.newDocumentBuilder().parse(var9).getDocumentElement()) != null) {
                    var14 = zzXgN(var13);
                } else {
                    var14 = new ArrayList();
                }

                return (List)var14;
            } catch (Exception var15) {
                throw new zzXCv(1006, var7 + zzZj0.zzYtN().zzZYr(new byte[]{69, 32, 114, 114, 114, 111, 105, 32, 32, 110, 111, 99, 118, 110, 114, 101, 105, 116, 103, 110, 114, 32, 115, 101, 111, 112, 115, 110, 32, 101, 115, 106, 110, 111, 118, 32, 108, 97, 101, 117, 116, 32, 32, 111, 97, 106, 97, 118, 111, 32, 106, 98, 99, 101, 32, 116, 32, 58}) + var15.getMessage());
            }
        }
    }

    private zzfq zzZSY(Long var1) throws zzXCv {
        zzW26 var3 = zzW26.zzWAe(new zzK9(this.zz1h, this.zzYxm), this.zzZAL, false);
        String var4 = zzZj0.zzYtN().zzZYr(new byte[]{112, 47, 111, 114, 117, 100, 116, 99, 47, 115, 105, 123, 125, 100}).replace(zzZj0.zzYtN().zzZYr(new byte[]{102, 123, 114, 111, 97, 109, 125, 116}), zzZj0.zzYtN().zzZYr(new byte[]{109, 120, 108})).replaceAll(zzZj0.zzYtN().zzZYr(new byte[]{42, 92}), "");
        String var5 = zzZj0.zzYtN().zzZYr(new byte[]{69, 71, 84});
        HashMap var6 = new HashMap();
        new HashMap();
        if (var1 != null) {
            var4 = var4.replace("{id}", zzW26.zzYBY(var1));
        }

        var4 = var4.replaceAll("\\{\\w*\\}", "");
        String var7;
        if ((var7 = var3.zzWAe(var4, var5, var6, (String)null)).length() == 0) {
            return null;
        } else {
            try {
                StringReader var8 = new StringReader(var7);
                InputSource var9 = new InputSource(var8);
                DocumentBuilderFactory var10;
                (var10 = DocumentBuilderFactory.newInstance()).setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                Element var13 = var10.newDocumentBuilder().parse(var9).getDocumentElement();
                zzfq var14 = null;
                if (var13 != null) {
                    var14 = this.zzWRm(var13);
                }

                return var14;
            } catch (Exception var15) {
                throw new zzXCv(1006, var7 + zzZj0.zzYtN().zzZYr(new byte[]{69, 32, 114, 114, 114, 111, 105, 32, 32, 110, 111, 99, 118, 110, 114, 101, 105, 116, 103, 110, 114, 32, 115, 101, 111, 112, 115, 110, 32, 101, 115, 106, 110, 111, 118, 32, 108, 97, 101, 117, 116, 32, 32, 111, 97, 106, 97, 118, 111, 32, 106, 98, 99, 101, 32, 116, 32, 58}) + var15.getMessage());
            }
        }
    }

    final void zzoy() {
        if (this.zzWgt != null && this.zzYT7 != null) {
            BigDecimal var1 = zzWQQ.zzFo().zzWho();
            long var2 = zzWQQ.zzFo().zzWA3();
            if (var1.compareTo(BigDecimal.ZERO) != 0 || var2 != 0L) {
                boolean var4 = true;
                boolean var5 = false;
                boolean var6 = false;

                try {
                    List var7;
                    if (!zzOk(var7 = this.zzZYr(this.zzWgt, this.zzYT7))) {
                        zzWww = new zzXyh();
                        return;
                    }

                    if (var7 != null && var7.size() > 0 && ((zzW3g)var7.get(0)).zzX6m() != null && ((zzW3g)var7.get(0)).zzX6m().size() > 0 && ((zzZgY)((zzW3g)var7.get(0)).zzX6m().get(0)).zzWc4() != null) {
                        zz6f var54 = ((zzZgY)((zzW3g)var7.get(0)).zzX6m().get(0)).zzWc4();
                        zzfq var9;
                        if ((var9 = this.zzZSY(var54.zzri())) != null) {
                            Iterator var10 = var9.zzYGL().iterator();

                            while(var10.hasNext()) {
                                zz6f var11;
                                if ((var11 = (zz6f)var10.next()).zzw4() == var54.zzw4()) {
                                    var54 = var11;
                                    break;
                                }
                            }
                        }

                        ArrayList var55 = new ArrayList();
                        Iterator var56 = var54.zzFr().iterator();

                        while(true) {
                            while(var56.hasNext()) {
                                zzWrR var12 = (zzWrR)var56.next();
                                if (var1.compareTo(BigDecimal.ZERO) > 0 && zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 99, 111, 115, 101, 101, 115, 32, 100, 117, 81, 110, 97, 105, 116, 121, 116}).equals(var12.getName())) {
                                    var55.add(var12);
                                } else if (var2 > 0L && zzZj0.zzYtN().zzZYr(new byte[]{114, 67, 100, 101, 116, 105, 115}).equals(var12.getName())) {
                                    var55.add(var12);
                                }
                            }

                            if (var55.size() <= 0 || var1.compareTo(BigDecimal.ZERO) <= 0 && var2 <= 0L) {
                                break;
                            }

                            ArrayList var57 = new ArrayList();
                            Iterator var58 = var55.iterator();

                            while(var58.hasNext()) {
                                zzWrR var13 = (zzWrR)var58.next();
                                zzXjx var14;
                                if (zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 99, 111, 115, 101, 101, 115, 32, 100, 117, 81, 110, 97, 105, 116, 121, 116}).equals(var13.getName())) {
                                    (var14 = new zzXjx()).zzZu1(var13.zzw4());
                                    var14.zzWAe(var1);
                                    var14.zzYjN(((zzW3g)var7.get(0)).zzw4());
                                    var14.zzYEj(true);
                                    var57.add(var14);
                                } else if (zzZj0.zzYtN().zzZYr(new byte[]{114, 67, 100, 101, 116, 105, 115}).equals(var13.getName())) {
                                    (var14 = new zzXjx()).zzZu1(var13.zzw4());
                                    var14.zzWAe(BigDecimal.valueOf(var2));
                                    var14.zzYjN(((zzW3g)var7.get(0)).zzw4());
                                    var14.zzYEj(true);
                                    var57.add(var14);
                                }
                            }

                            try {
                                this.zzWAe(((zzW3g)var7.get(0)).zzw4(), var57);
                                var4 = false;
                            } catch (Exception var50) {
                                var5 = true;
                            }
                            break;
                        }
                    }

                    var6 = true;
                } catch (zzXCv var52) {
                    if (var52.zz7G() == 500) {
                        boolean var8 = false;
                        zzWQQ.zzFo().zzXGK.lock();

                        try {
                            if (zzWQQ.zzFo().zzXEL == -1L) {
                                zzWQQ.zzFo().zzXEL = System.currentTimeMillis();
                            }

                            if (System.currentTimeMillis() - zzWQQ.zzFo().zzXEL > 86400000L) {
                                var8 = true;
                            }
                        } finally {
                            zzWQQ.zzFo().zzXGK.unlock();
                        }

                        if (var8) {
                            var5 = true;
                        }
                    } else {
                        var5 = true;
                    }
                } catch (Exception var53) {
                    var5 = true;
                }

                if (var4) {
                    zzWQQ.zzFo().zzWAe(var1, false);
                    zzWQQ.zzFo().zzWRm(var2, false);
                }

                if (var5) {
                    zzWQQ.zzFo().zzXnD.lock();

                    try {
                        if (zzWQQ.zzFo().zzWrE == -1L) {
                            zzWQQ.zzFo().zzWrE = System.currentTimeMillis();
                        }

                        ++zzWQQ.zzFo().zzZBb;
                        if (System.currentTimeMillis() - zzWQQ.zzFo().zzWrE > 86400000L && zzWQQ.zzFo().zzZBb > 10) {
                            zzWww = new zzXyh();
                        }
                    } finally {
                        zzWQQ.zzFo().zzXnD.unlock();
                    }
                } else {
                    zzWQQ.zzFo().zzXnD.lock();

                    try {
                        zzWQQ.zzFo().zzWrE = -1L;
                        zzWQQ.zzFo().zzZBb = 0;
                    } finally {
                        zzWQQ.zzFo().zzXnD.unlock();
                    }
                }

                if (var6) {
                    zzWQQ.zzFo().zzXGK.lock();

                    try {
                        zzWQQ.zzFo().zzXEL = -1L;
                    } finally {
                        zzWQQ.zzFo().zzXGK.unlock();
                    }

                }
            }
        }
    }

    private void zzWAe(Long var1, List<zzXjx> var2) throws zzXCv {
        zzW26 var4 = zzW26.zzWAe(new zzK9(this.zz1h, this.zzYxm), this.zzZAL, false);
        String var5 = zzZj0.zzYtN().zzZYr(new byte[]{115, 47, 98, 117, 99, 115, 105, 114, 116, 112, 111, 105, 115, 110, 123, 47, 100, 105, 47, 125, 116, 105, 109, 101, 115}).replace(zzZj0.zzYtN().zzZYr(new byte[]{102, 123, 114, 111, 97, 109, 125, 116}), zzZj0.zzYtN().zzZYr(new byte[]{115, 106, 110, 111})).replaceAll(zzZj0.zzYtN().zzZYr(new byte[]{42, 92}), "");
        String var6 = zzZj0.zzYtN().zzZYr(new byte[]{85, 80, 84});
        HashMap var7 = new HashMap();
        new HashMap();
        var5 = var5.replace("{id}", zzW26.zzYBY(var1)).replaceAll("\\{\\w*\\}", "");
        String var8 = zzWyr(var2);
        var4.zzWAe(var5, var6, var7, var8);
    }

    private zzW3g zzYp8(Node var1) {
        zzW3g var2 = new zzW3g();
        NodeList var3 = var1.getChildNodes();

        for(int var4 = 0; var4 < var3.getLength(); ++var4) {
            Node var5 = var3.item(var4);
            if (!zzZj0.zzYtN().zzZYr(new byte[]{117, 83, 115, 98, 114, 99, 112, 105, 105, 116, 110, 111, 114, 80, 99, 105, 110, 105, 80, 103, 97, 108, 115, 110}).equals(var5.getNodeName())) {
                if (zzZj0.zzYtN().zzZYr(new byte[]{100, 73}).equals(var5.getNodeName())) {
                    var2.zzYdW(Long.parseLong(var5.getTextContent()));
                } else if (zzZj0.zzYtN().zzZYr(new byte[]{105, 70, 116, 108, 114, 101, 100, 101, 116, 83, 116, 97, 115, 117}).equals(var5.getNodeName())) {
                    var2.zzZJf(var5.getTextContent());
                }
            } else {
                NodeList var6 = var5.getChildNodes();

                for(int var7 = 0; var7 < var6.getLength(); ++var7) {
                    Node var8 = var6.item(var7);
                    zzZgY var9 = new zzZgY();
                    var2.zzX6m().add(var9);
                    NodeList var10 = var8.getChildNodes();

                    for(int var11 = 0; var11 < var10.getLength(); ++var11) {
                        Node var12 = var10.item(var11);
                        if (zzZj0.zzYtN().zzZYr(new byte[]{100, 73}).equals(var12.getNodeName())) {
                            Long.parseLong(var12.getTextContent());
                        } else if (zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 100, 111, 99, 117, 80, 116, 105, 114, 105, 99, 103, 110, 108, 80, 110, 97}).equals(var12.getNodeName())) {
                            var9.zzWAe(zzVI(var12));
                        }
                    }
                }
            }
        }

        return var2;
    }

    private zzfq zzWRm(Node var1) {
        zzfq var2 = new zzfq();
        NodeList var3 = var1.getChildNodes();

        for(int var4 = 0; var4 < var3.getLength(); ++var4) {
            Node var5 = var3.item(var4);
            if (zzZj0.zzYtN().zzZYr(new byte[]{100, 73}).equals(var5.getNodeName())) {
                Long.parseLong(var5.getTextContent());
            } else if (zzZj0.zzYtN().zzZYr(new byte[]{97, 78, 101, 109}).equals(var5.getNodeName())) {
                var5.getTextContent();
            } else if (zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 99, 105, 110, 105, 80, 103, 97, 108, 115, 110}).equals(var5.getNodeName())) {
                NodeList var6 = var5.getChildNodes();

                for(int var7 = 0; var7 < var6.getLength(); ++var7) {
                    Node var8 = var6.item(var7);
                    var2.zzYGL().add(zzVI(var8));
                }
            }
        }

        return var2;
    }

    private static zz6f zzVI(Node var0) {
        zz6f var1 = new zz6f();
        NodeList var2 = var0.getChildNodes();

        for(int var3 = 0; var3 < var2.getLength(); ++var3) {
            Node var4 = var2.item(var3);
            if (zzZj0.zzYtN().zzZYr(new byte[]{100, 73}).equals(var4.getNodeName())) {
                var1.zzYdW(Long.parseLong(var4.getTextContent()));
            } else if (zzZj0.zzYtN().zzZYr(new byte[]{97, 78, 101, 109}).equals(var4.getNodeName())) {
                var4.getTextContent();
            } else if (zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 100, 111, 99, 117, 73, 116, 100}).equals(var4.getNodeName())) {
                var1.zzXH5(Long.parseLong(var4.getTextContent()));
            } else if (zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 100, 111, 99, 117, 73, 116, 101, 116, 115, 109, 105, 76, 116, 115}).equals(var4.getNodeName())) {
                NodeList var5 = var4.getChildNodes();

                for(int var6 = 0; var6 < var5.getLength(); ++var6) {
                    Node var7 = var5.item(var6);
                    zzWrR var8 = new zzWrR();
                    var1.zzFr().add(var8);
                    NodeList var9 = var7.getChildNodes();

                    for(int var10 = 0; var10 < var9.getLength(); ++var10) {
                        Node var11 = var9.item(var10);
                        if (zzZj0.zzYtN().zzZYr(new byte[]{100, 73}).equals(var11.getNodeName())) {
                            var8.zzYdW(Long.parseLong(var11.getTextContent()));
                        } else if (zzZj0.zzYtN().zzZYr(new byte[]{97, 78, 101, 109}).equals(var11.getNodeName())) {
                            var8.setName(var11.getTextContent());
                        }
                    }
                }
            }
        }

        return var1;
    }

    private static List<zzXjx> zzXgN(Node var0) {
        ArrayList var1 = new ArrayList();
        NodeList var2 = var0.getChildNodes();

        for(int var3 = 0; var3 < var2.getLength(); ++var3) {
            Node var4 = var2.item(var3);
            zzXjx var5 = new zzXjx();
            var1.add(var5);
            NodeList var6 = var4.getChildNodes();

            for(int var7 = 0; var7 < var6.getLength(); ++var7) {
                Node var8;
                String var9 = (var8 = var6.item(var7)).getNodeName();
                String var10 = var8.getTextContent();
                if (zzZj0.zzYtN().zzZYr(new byte[]{117, 83, 115, 98, 114, 99, 112, 105, 105, 116, 110, 111, 100, 73}).equals(var9)) {
                    var5.zzYjN(Long.parseLong(var10));
                } else if (zzZj0.zzYtN().zzZYr(new byte[]{114, 80, 100, 111, 99, 117, 73, 116, 101, 116, 73, 109, 100}).equals(var9)) {
                    var5.zzZu1(Long.parseLong(var10));
                } else if (zzZj0.zzYtN().zzZYr(new byte[]{117, 81, 110, 97, 105, 116, 121, 116}).equals(var9)) {
                    var5.zzWAe(new BigDecimal(var10));
                } else if (zzZj0.zzYtN().zzZYr(new byte[]{112, 85, 97, 100, 101, 116, 101, 68, 99, 115, 105, 114, 116, 112, 111, 105, 110}).equals(var9)) {
                    var5.zzYzm(var10);
                } else if (zzZj0.zzYtN().zzZYr(new byte[]{97, 78, 101, 109}).equals(var9)) {
                    var5.setName(var10);
                } else if (zzZj0.zzYtN().zzZYr(new byte[]{110, 85, 116, 105, 97, 78, 101, 109}).equals(var9)) {
                    var5.zzX89(var10);
                } else if (zzZj0.zzYtN().zzZYr(new byte[]{115, 73, 117, 81, 110, 97, 105, 116, 121, 116, 99, 65, 117, 99, 117, 109, 97, 108, 101, 116, 100}).equals(var9)) {
                    var5.zzYEj(Boolean.parseBoolean(var10));
                }
            }
        }

        return var1;
    }

    private static String zzWyr(List<zzXjx> var0) {
        StringBuilder var1;
        (var1 = new StringBuilder()).append(zzZj0.zzYtN().zzZYr(new byte[]{63, 60, 109, 120, 32, 108, 101, 118, 115, 114, 111, 105, 61, 110, 49, 34, 48, 46, 32, 34, 110, 101, 111, 99, 105, 100, 103, 110, 34, 61, 116, 117, 45, 102, 34, 56, 62, 63}));
        var1.append(zzZj0.zzYtN().zzZYr(new byte[]{65, 60, 114, 114, 121, 97, 102, 79, 117, 83, 115, 98, 114, 99, 112, 105, 105, 116, 110, 111, 116, 73, 109, 101, 120, 32, 108, 109, 115, 110, 120, 58, 100, 115, 34, 61, 116, 104, 112, 116, 47, 58, 119, 47, 119, 119, 119, 46, 46, 51, 114, 111, 47, 103, 48, 50, 49, 48, 88, 47, 76, 77, 99, 83, 101, 104, 97, 109, 32, 34, 109, 120, 110, 108, 58, 115, 115, 120, 61, 105, 104, 34, 116, 116, 58, 112, 47, 47, 119, 119, 46, 119, 51, 119, 111, 46, 103, 114, 50, 47, 48, 48, 47, 49, 77, 88, 83, 76, 104, 99, 109, 101, 45, 97, 110, 105, 116, 115, 110, 97, 101, 99, 62, 34}));
        Iterator var2 = var0.iterator();

        while(var2.hasNext()) {
            zzXjx var3 = (zzXjx)var2.next();
            var1.append(zzZj0.zzYtN().zzZYr(new byte[]{83, 60, 98, 117, 99, 115, 105, 114, 116, 112, 111, 105, 73, 110, 101, 116, 62, 109}));
            if (var3.zzYpP() != null) {
                var1.append(zzZj0.zzYtN().zzZYr(new byte[]{85, 60, 100, 112, 116, 97, 68, 101, 115, 101, 114, 99, 112, 105, 105, 116, 110, 111, 62}) + var3.zzYpP() + zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 112, 85, 97, 100, 101, 116, 101, 68, 99, 115, 105, 114, 116, 112, 111, 105, 62, 110}));
            }

            var1.append(zzZj0.zzYtN().zzZYr(new byte[]{73, 60, 81, 115, 97, 117, 116, 110, 116, 105, 65, 121, 99, 99, 109, 117, 108, 117, 116, 97, 100, 101, 62}) + String.valueOf(var3.zzW91()) + zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 115, 73, 117, 81, 110, 97, 105, 116, 121, 116, 99, 65, 117, 99, 117, 109, 97, 108, 101, 116, 62, 100}));
            if (var3.getName() != null) {
                var1.append(zzZj0.zzYtN().zzZYr(new byte[]{78, 60, 109, 97, 62, 101}) + var3.getName() + zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 97, 78, 101, 109, 62}));
            }

            var1.append(zzZj0.zzYtN().zzZYr(new byte[]{80, 60, 111, 114, 117, 100, 116, 99, 116, 73, 109, 101, 100, 73, 62}) + String.valueOf(var3.zzMG()) + zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 114, 80, 100, 111, 99, 117, 73, 116, 101, 116, 73, 109, 62, 100}));
            var1.append(zzZj0.zzYtN().zzZYr(new byte[]{81, 60, 97, 117, 116, 110, 116, 105, 62, 121}) + (var3.zzZIq() == null ? zzZj0.zzYtN().zzZYr(new byte[]{117, 110, 108, 108}) : String.format(Locale.US, zzZj0.zzYtN().zzZYr(new byte[]{102, 37}), var3.zzZIq())) + zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 117, 81, 110, 97, 105, 116, 121, 116, 62}));
            var1.append(zzZj0.zzYtN().zzZYr(new byte[]{83, 60, 98, 117, 99, 115, 105, 114, 116, 112, 111, 105, 73, 110, 62, 100}) + String.valueOf(var3.zzZK3()) + zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 117, 83, 115, 98, 114, 99, 112, 105, 105, 116, 110, 111, 100, 73, 62}));
            if (var3.zzYy5() != null) {
                var1.append(zzZj0.zzYtN().zzZYr(new byte[]{85, 60, 105, 110, 78, 116, 109, 97, 62, 101}) + var3.zzYy5() + zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 110, 85, 116, 105, 97, 78, 101, 109, 62}));
            }

            var1.append(zzZj0.zzYtN().zzZYr(new byte[]{67, 60, 97, 104, 103, 110, 115, 101, 105, 72, 116, 115, 114, 111, 32, 121, 62, 47}));
            var1.append(zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 117, 83, 115, 98, 114, 99, 112, 105, 105, 116, 110, 111, 116, 73, 109, 101, 62}));
        }

        var1.append(zzZj0.zzYtN().zzZYr(new byte[]{47, 60, 114, 65, 97, 114, 79, 121, 83, 102, 98, 117, 99, 115, 105, 114, 116, 112, 111, 105, 73, 110, 101, 116, 62, 109}));
        return var1.toString();
    }

    private void zzZ4h() throws Exception {
        Class var1 = this.getClass();
        if (this.zzVVD == 0L) {
            this.zzY0w(Math.random() * 10.0D + 1.0D);
            this.zzVVD = 1761636096L ^ this.zzZ4;
        }

        String var2 = var1.getProtectionDomain().getCodeSource().getLocation().toString();
        String var3 = zzZj0.zzYtN().zzXIn(new byte[]{46, 106, 97, 114});
        int var4;
        if ((var4 = var2.indexOf(var3)) >= 0) {
            if (var2.indexOf(var3, var4 + var3.length()) >= 0) {
                int var5 = 0;

                ZipInputStream var6;
                for(ZipEntry var7 = (var6 = new ZipInputStream(var1.getProtectionDomain().getCodeSource().getLocation().openStream())).getNextEntry(); var7 != null; var7 = var6.getNextEntry()) {
                    String var8;
                    if ((var8 = var7.getName()).startsWith(zzZj0.zzYtN().zzXIn(new byte[]{77, 69, 84, 65, 45, 73, 78, 70, 47})) && (var8.endsWith(zzZj0.zzYtN().zzXIn(new byte[]{46, 83, 70})) || var8.endsWith(zzZj0.zzYtN().zzXIn(new byte[]{46, 82, 83, 65})))) {
                        ++var5;
                    }

                    if (var5 >= 2) {
                        break;
                    }
                }

                if (var5 < 2) {
                    throw new IllegalStateException();
                }

                return;
            }

            if (var1.getProtectionDomain().getCodeSource().getCertificates() == null) {
                throw new IllegalStateException();
            }
        }

    }

    private void zzY0w(double var1) {
        this.zzZ4 = (long)var1;
        this.zzZ4 ^= this.zzZ4 << 21;
        this.zzZ4 ^= this.zzZ4 >>> 35;
        this.zzZ4 ^= this.zzZ4 << 4;
    }

    static zzXyh zzVXL() {
        return zzWww;
    }

    static {
        zzYat = (zzWww = new zzXyh()).zzY0F;
    }
}
