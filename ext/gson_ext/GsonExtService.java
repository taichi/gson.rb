/*
 *     Copyright 2012 Couchbase, Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
import gson_ext.Decoder;
import gson_ext.Encoder;

import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyModule;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;

public class GsonExtService implements BasicLibraryService {

    public boolean basicLoad(final Ruby ruby) throws IOException {
        RubyModule gson = ruby.defineModule("Gson");

        gson.defineClassUnder("Encoder", ruby.getObject(), new ObjectAllocator() {
            public IRubyObject allocate(Ruby ruby, RubyClass rubyClass) {
                return new Encoder(ruby, rubyClass);
            }
        }).defineAnnotatedMethods(Encoder.class);

        gson.defineClassUnder("Decoder", ruby.getObject(), new ObjectAllocator() {
            public IRubyObject allocate(Ruby ruby, RubyClass rubyClass) {
                return new Decoder(ruby, rubyClass);
            }
        }).defineAnnotatedMethods(Decoder.class);

        RubyClass standardError = ruby.getStandardError();
        gson.defineClassUnder("DecodeError", standardError, standardError.getAllocator());
        gson.defineClassUnder("EncodeError", standardError, standardError.getAllocator());

        return true;
    }
}
